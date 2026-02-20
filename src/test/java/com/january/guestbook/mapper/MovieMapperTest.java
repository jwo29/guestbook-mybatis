package com.january.guestbook.mapper;

import com.january.guestbook.domain.Movie;
import com.january.guestbook.domain.MovieImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class MovieMapperTest {

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private MovieImageMapper movieImageMapper;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> {

            Movie movie = Movie.builder().title("Movie.... " + i).build();

            System.out.println("-------------------------------------------------");

            movieMapper.insert(movie);

            int count = (int) (Math.random() * 5) + 1; // 1, 2, 3, 4, 5

            for (int j = 0; j < count; j++) {
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("test" + j + ".jpg")
                        .build();

                movieImageMapper.insert(movieImage);
            }

            System.out.println("-------------------------------------------------");
        });
    }

}
