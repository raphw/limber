function limberAnswer(xml) {
    var parsedXml = jQuery.parseXML(xml);
    var parsed = jQuery(parsedXml);
    parsed.children('updates').children('update').each(
        function (index, element) {

            var $update = jQuery(element);

            var identification = $update.children('identification').get(0).innerHTML;
            var id = $update.children('location').get(0).innerHTML;
            var action = $update.children('action').get(0).innerHTML;
            var content = $update.children('content').get(0).innerHTML.replace('<![CDATA[', '').replace(']]>', '');

            $target = jQuery(document).xpath(id);
            $target.replaceWith(content);
        }
    );
}