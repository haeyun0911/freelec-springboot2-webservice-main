package com.jojoldu.book.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort; // 이제 사용하지 않음
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// webEnvironment의 기본값은 MOCK 이므로, MockMvc 사용 시 생략하거나 MOCK으로 명시합니다.
@SpringBootTest
public class PostsApiControllerTest {

    // TestRestTemplate은 더 이상 사용하지 않으므로 관련 코드 삭제
    // @LocalServerPort private int port;
    // @Autowired private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    // 각 테스트 시작 전 MockMvc 인스턴스 생성
    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER") // ROLE_USER 권한을 가진 모의 사용자
    public void Posts_등록된다() throws Exception {
        // given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        // API URL (상대 경로 사용)
        String url = "/api/v1/posts";

        // when - MockMvc로 POST 요청 실행
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON) // JSON 타입으로 요청
                        .content(new ObjectMapper().writeValueAsString(requestDto))) // 요청 본문에 DTO를 JSON으로 변환하여 담음
                .andExpect(status().isOk()); // HTTP 상태 코드가 200 OK 인지 검증

        // then - DB 검증
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER") // ROLE_USER 권한을 가진 모의 사용자
    public void Posts_수정된다() throws Exception {
        // given
        // 테스트용 데이터 미리 저장
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        // API URL (상대 경로 사용)
        String url = "/api/v1/posts/" + updateId;

        // when - MockMvc로 PUT 요청 실행
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON) // JSON 타입으로 요청
                        .content(new ObjectMapper().writeValueAsString(requestDto))) // 요청 본문에 DTO를 JSON으로 변환하여 담음
                .andExpect(status().isOk()); // HTTP 상태 코드가 200 OK 인지 검증

        // then - DB 검증
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}