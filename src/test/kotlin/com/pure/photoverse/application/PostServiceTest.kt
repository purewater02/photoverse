package com.pure.photoverse.application

import com.pure.photoverse.domain.Post
import com.pure.photoverse.domain.User
import com.pure.photoverse.dto.request.CreatePostRequest
import com.pure.photoverse.repository.post.PostRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class PostServiceTest {
    private lateinit var postService: PostService
    private lateinit var postRepository: PostRepository
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        postRepository = mockk()
        userService = mockk()
        postService = PostService(userService, postRepository)

        every { userService.getCurrentUser() } returns
            User(
                uid = "6TBYALo50RQcBZPFSSwEQH57qBm1",
                signInProvider = User.SignInProvider.GOOGLE,
                username = "PW_dev",
                email = "ksmin02@gmail.com",
                profileImage = "https://lh3.googleusercontent.com/a/ACg8ocIbS1RMgCDyi_dk2B4DwF8gUuNssJeMFLZYXIAiy9j3IS7vWL_f=s96-c",
                caption = "caption",
            )

        every { postRepository.save(any()) } answers {
            val post = arg<Post>(0)

            spyk<Post>(post) {
                every { this@spyk.id } returns 1L
                every { this@spyk.createdAt } returns LocalDateTime.now()
            }
        }
    }

    @Test
    @DisplayName("포스트 생성이 정상 동작한다.")
    fun createPost() {
        // given
        val request = CreatePostRequest(listOf("https://example.com/image1.jpg", "https://example.com/image2.jpg"), "test caption")

        // when
        val response = postService.createPost(request)

        // then
        assertNotNull(response)
        assertThat(request.images[0]).isEqualTo(response.images[0])
        assertThat(request.images[1]).isEqualTo(response.images[1])
        assertThat(request.caption).isEqualTo(response.caption)
    }
}
