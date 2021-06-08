package com.mustr;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
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
