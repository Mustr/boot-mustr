package com.mustr;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootMustrApplicationTests {

    @Autowired
    DataSource dataSource;
    
	@Test
	public void contextLoads() throws SQLException {
	    System.out.println(dataSource);
	    
	    Connection connection = dataSource.getConnection();
	    System.out.println(connection);
	    
	    connection.close();
	}

}
