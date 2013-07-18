var Limber = (function () {

    var sloppyXPathInterpreter = (function () {

        var PATH_ELEMENT_SEPARATOR = '/';
        var PATH_ELEMENT_REGEX = new RegExp('([a-zA-Z]+[a-zA-Z0-9]*)\\[([0-9]+)\\]');

        function validateSelection($cursor) {
            if ($cursor.size() != 1) {
                throw 'Selection of path element is invalid: matches ' + $cursor.size()
                    + ' elements while one was expected';
            }
        }

        function childExists($cursor, element) {
            validateSelection($cursor);
            var $movedCursor = $cursor.children(element.name);
            if ($movedCursor.size() > element.index) {
                return jQuery($movedCursor.get(element.index));
            } else if ($cursor.children().size() == 1) {
                return childExists(jQuery($cursor.children().get(0)), element);
            } else {
                throw 'Cannot browse to \'' + element.name + '\' at index ' + element.index + '\'';
            }
        }

        function parsePath(rawPath) {
            var nodes = rawPath.split(PATH_ELEMENT_SEPARATOR);
            var elements = [];
            var index = 0;
            jQuery(nodes).each(function (key, element) {
                if (element.length == 0) {
                    return true;
                }
                if (!PATH_ELEMENT_REGEX.test(element)) {
                    throw 'Path element does not match regex: ' + element;
                }
                var matched = PATH_ELEMENT_REGEX.exec(element);
                elements[index++] = { name: matched[1], index: matched[2] };
                return true;
            });
            return elements;
        }

        function findElement(input) {

            var elements = parsePath(input);
            var $cursor = jQuery(document);
            jQuery(elements).each(function (key, element) {
                $cursor = childExists($cursor, element);
            });

            validateSelection($cursor);
            return $cursor.get(0);
        }

        return {
            find: function (input) {
                return findElement(input);
            }
        };
    })();

    var XHR_ACTION_XPATH = 'xpath';
    var XHR_ACTION_SELECTOR = 'selector';

    function identifyTarget(locationType, locationId) {

        var $target;
        switch (locationType) {
            case XHR_ACTION_XPATH:
                $target = jQuery(sloppyXPathInterpreter.find(locationId));
                break;
            case XHR_ACTION_SELECTOR:
                $target = jQuery(locationId);
                break;
            default:
                throw 'Unknown target identification type \'' + locationType + '\'';
        }

        if ($target.size() != 1) {
            throw 'Could not identify element with location id \''
                + locationId + '\' and location type \''
                + locationType + '\'';
        }

        return $target;
    }

    var xhrProcessor = (function () {

        var XHR_ROOT_TAG_NAME = 'updates';
        var XHR_UPDATE_TAG_NAME = 'update';
        var XHR_LOCATION_TYPE_TAG_NAME = 'locationType';
        var XHR_LOCATION_ID_TAG_NAME = 'locationId';
        var XHR_ACTION_TAG_NAME = 'action';
        var XHR_CONTENT_TAG_NAME = 'content';

        var XHR_ACTION_REPLACE = 'replace';

        var CDATA_START_TAG = '<![CDATA[';
        var CDATA_CLOSE_TAG = ']]>';

        function findUpdateNodes(xml) {
            var parsed = jQuery(jQuery.parseXML(xml));
            var navigation = parsed.children(XHR_ROOT_TAG_NAME);
            if (navigation.size() != 1) {
                throw 'There were ' + navigation.size() + ' \'' + XHR_ROOT_TAG_NAME + '\' nodes found while one was expected';
            }
            return navigation.children(XHR_UPDATE_TAG_NAME);
        }

        function extractHtml($element, identifier) {
            var $child = $element.children(identifier);
            if ($child.size() != 1) {
                throw XHR_UPDATE_TAG_NAME + ' element did contain ' + $child.size() + ' elements with identifier \''
                    + identifier + '\' where one was expected';
            }
            $child = jQuery($child.get(0));
            if ($child.children().size() != 0) {
                throw XHR_UPDATE_TAG_NAME + ' element with identifier \'' + identifier + '\' contained nested elements';
            }
            return jQuery.trim($child.html());
        }

        function stripCharacterDataTag(value) {
            if (value.substring(0, CDATA_START_TAG.length) == CDATA_START_TAG
                && value.substring(value.length - CDATA_CLOSE_TAG.length, value.length) == CDATA_CLOSE_TAG) {
                value = value.substring(CDATA_START_TAG.length, value.length - CDATA_CLOSE_TAG.length);
            } else {
                throw 'Could not remove CDATA section from content';
            }
            return value;
        }

        function describeUpdate($update) {
            return {
                locationType: extractHtml($update, XHR_LOCATION_TYPE_TAG_NAME),
                locationId: extractHtml($update, XHR_LOCATION_ID_TAG_NAME),
                action: extractHtml($update, XHR_ACTION_TAG_NAME),
                content: stripCharacterDataTag(extractHtml($update, XHR_CONTENT_TAG_NAME))
            };
        }

        function applyUpdate($target, action, content) {
            switch (action) {
                case XHR_ACTION_REPLACE:
                    $target.replaceWith(content);
                    break;
                default:
                    throw 'Cannot apply update type \'' + action + '\'';
            }
        }

        function process(xml) {
            try {
                findUpdateNodes(xml).each(function (index, element) {
                        try {
                            var updateDescription = describeUpdate(jQuery(element));
                            var $target = jQuery(identifyTarget(updateDescription.locationType, updateDescription.locationId));
                            applyUpdate($target, updateDescription.action, updateDescription.content);
                            return true;
                        } catch (message) {
//                            console.log('Could not apply update: ' + message);
                            return false;
                        }
                    }
                );
            } catch (error) {
//                console.log(error);
                return false;
            }
            return true;
        }

        return {
            process: function (xml) {
                return process(xml);
            }
        };
    })();

    var xhrBinder = (function () {
        function bindEvent($target, eventDescription, callbackUrl) {
            $target.bind(eventDescription, function () {
                jQuery.ajax({
                    url: callbackUrl,
                    success: function (xml) {
                        xhrProcessor.process(xml);
                    }
                });
            });
        }

        function bind(locationType, locationId, eventDescription, callbackUrl) {
            var $target;
            try {
                $target = identifyTarget(locationType, locationId);
            } catch (message) {
//                console.log('Exception: ' + message);
                return false;
            }
            try {
                bindEvent($target, eventDescription, callbackUrl);
            } catch (error) {
//                console.log(error);
                return false;
            }
            return true;
        }

        return {
            bind: function (locationType, locationId, eventDescription, callbackUrl) {
                return bind(locationType, locationId, eventDescription, callbackUrl);
            }
        };
    })();

    return {
        getXhrBinder: function () {
            return xhrBinder;
        }
    };
})();
