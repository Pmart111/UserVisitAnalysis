package sample.VisitInformation;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class WhenInformationTest {
    private WhenInformation whenInformation;
    @Before
    public void init() {
        whenInformation = new WhenInformation();
        whenInformation.setTimeAfterBannerDisplay(new Date(0));
        whenInformation.setDate(new Date(0));
    }
    @Test
    public void testGetDate() throws Exception {
        assertEquals(0, whenInformation.getDate().getTime());
    }

    @Test
    public void testSetDate() throws Exception {
        whenInformation.setDate(new Date(1));
        assertEquals(1, whenInformation.getDate().getTime());
        whenInformation.setDate(new Date(0));
    }

    @Test
    public void testGetTimeAfterBannerDisplay() throws Exception {
        assertEquals(0, whenInformation.getTimeAfterBannerDisplay().getTime());
    }

    @Test
    public void testSetTimeAfterBannerDisplay() throws Exception {
        whenInformation.setTimeAfterBannerDisplay(new Date(1));
        assertEquals(1, whenInformation.getTimeAfterBannerDisplay().getTime());
        whenInformation.setTimeAfterBannerDisplay(new Date(0));
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("0 0", whenInformation.toString());
    }
}