package com.rjs.movieinfoservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void movieFound(){
		MovieInfo movieInfo = restTemplate.getForObject("http://localhost:" + port + "/movies/Jaws",
				MovieInfo.class);

		MovieInfo expectedMovieInfo = new MovieInfo(
				"Jaws",
				"A man eating shark terrorises seaside resort.");

		assertThat(movieInfo).isEqualTo(expectedMovieInfo);
	}

	@Test
	void movieNotFound(){
		ResponseEntity<MovieInfo> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/movies/foo", MovieInfo.class);
		assertThat(HttpStatus.NOT_FOUND).isEqualTo( responseEntity.getStatusCode());
	}
}