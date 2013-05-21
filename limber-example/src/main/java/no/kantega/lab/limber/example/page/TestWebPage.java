package no.kantega.lab.limber.example.page;

import no.kantega.lab.limber.page.WebPage;

public class TestWebPage extends WebPage {

    private String content;

    public TestWebPage() {

        select().tag("h2").setContent("Title");

        String[] array = {"First", "second", "third"};
        StringBuilder sb = new StringBuilder();
        for (String a : array) {
            sb.append("<li>").append(a).append("</li>");
        }
        select().tag("ul").setContent(sb.toString());


    }
}
