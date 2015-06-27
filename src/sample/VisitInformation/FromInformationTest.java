package sample.VisitInformation;

import org.junit.Before;
import org.junit.Test;
import sample.VisitInformation.SiteActions.SiteActionFactory;

import static org.junit.Assert.*;

public class FromInformationTest {
    private FromInformation fromInformation;
    @Before
    public void init() {
        fromInformation = new FromInformation();
        fromInformation.setSiteName("http://site.html");
        fromInformation.setFromType(FromType.SITE);
    }

    @Test
    public void testGetSiteName() throws Exception {
        assertEquals(FromType.SITE, fromInformation.getFromType());
    }

    @Test
    public void testSetSiteName() throws Exception {
        fromInformation.setSiteName("http://site2.html");
        assertEquals("http://site2.html", fromInformation.getSiteName());
        fromInformation.setSiteName("http://site.html");

    }

    @Test
    public void testGetFromType() throws Exception {
        assertEquals(FromType.SITE, fromInformation.getFromType());
    }

    @Test
    public void testSetFromType() throws Exception {
        fromInformation.setFromType(FromType.OTHER);
        assertEquals(FromType.OTHER, fromInformation.getFromType());
        fromInformation.setFromType(FromType.SITE);

    }

    @Test
    public void testToString() throws Exception {
        assertEquals("http://site.html SITE", fromInformation.toString());
    }
}