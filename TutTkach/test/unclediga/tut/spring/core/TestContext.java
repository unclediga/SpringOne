package unclediga.tut.spring.core;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import unclediga.tut.spring.core.beans.Client;

import static org.junit.Assert.assertEquals;


public class TestContext {

    @Test
    public void testPropertyPlaceholderSystemOverride() {
        System.setProperty("id", "35");
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        Client client = ctx.getBean(Client.class);
        ctx.close();
        assertEquals(client.getId(), "35");

    }
}

