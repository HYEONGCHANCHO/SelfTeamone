package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Connection;
import java.sql.DriverManager;


@SpringBootTest
class DemoApplicationTests {

		private static final String DRIVER = "oracle.jdbc.OracleDriver";
		private static final String URL = "jdbc:oracle:thin:@192.168.0.21:1521/xe";
		private static final String UID = "teamone";
		private static final String PWD = "oneteam";

		@Test
		public void connectTest() {

			try (Connection con = DriverManager.getConnection(URL, UID, PWD)) {
				Class.forName(DRIVER);
				if(con != null) {
					System.out.println("DB 커넥션 성공");
					System.out.println("con : " + con);
				} else {
					System.out.println("DB정보를 받지 못헀습니다.");
					System.out.println("DB연결에 실패 했습니다.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}





}