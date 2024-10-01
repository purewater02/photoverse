package com.pure.photoverse.application

import com.pure.photoverse.domain.Post
import com.pure.photoverse.domain.User
import com.pure.photoverse.dto.record.PostUserTuple
import com.pure.photoverse.dto.request.CreatePostRequest
import com.pure.photoverse.repository.post.PostQueryDsl
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
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.time.LocalDateTime

@SpringBootTest
class PostServiceTest {
    private lateinit var postService: PostService
    private lateinit var postRepository: PostRepository
    private lateinit var userService: UserService
    private lateinit var postQueryDsl: PostQueryDsl

    @BeforeEach
    fun setUp() {
        postRepository = mockk()
        postQueryDsl = mockk()
        userService = mockk()
        postService = PostService(userService, postRepository, postQueryDsl)

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

    @Test
    @DisplayName("공개 포스트 조회가 정상 동작한다.")
    @Suppress("ktlint:standard:max-line-length")
    fun getPublicPosts() {
        // given
        val pageable = PageRequest.of(0, 2)
        val user =
            User(
                uid = "asdfasfdf",
                signInProvider = User.SignInProvider.GOOGLE,
                username = "user1",
                email = "example@test.com",
                profileImage = "https://pub-fd54887e43474ac1b6a70d31f1ab8554.r2.dev/image/0dd7b547-3d16-4285-94cb-e8fa3dbe26fd-shinano3D.jpg",
                caption = "example caption",
            )

        val post1 =
            Post.fixture(
                id = 1L,
                user = user,
                urls =
                    listOf(
                        "https://pub-fd54887e43474ac1b6a70d31f1ab8554.r2.dev/image/0dd7b547-3d16-4285-94cb-e8fa3dbe26fd-shinano3D.jpg",
                        "https://pub-fd54887e43474ac1b6a70d31f1ab8554.r2.dev/image/0dd7b547-3d16-4285-94cb-e8fa3dbe26fd-shinano3D.jpg",
                    ),
                caption = "post1 caption",
            )
        setPostId(post1, 1L)

        val post2 =
            Post.fixture(
                id = 2L,
                user = user,
                urls =
                    listOf(
                        "https://pub-fd54887e43474ac1b6a70d31f1ab8554.r2.dev/image/0dd7b547-3d16-4285-94cb-e8fa3dbe26fd-shinano3D.jpg",
                        "https://pub-fd54887e43474ac1b6a70d31f1ab8554.r2.dev/image/0dd7b547-3d16-4285-94cb-e8fa3dbe26fd-shinano3D.jpg",
                        "https://pub-fd54887e43474ac1b6a70d31f1ab8554.r2.dev/image/0dd7b547-3d16-4285-94cb-e8fa3dbe26fd-shinano3D.jpg",
                    ),
                caption = "post2 caption",
            )
        setPostId(post2, 2L)

        val postPage: Page<PostUserTuple> =
            PageImpl(
                listOf(
                    PostUserTuple(user, post1),
                    PostUserTuple(user, post2),
                ),
                pageable,
                3,
            )

        every { postQueryDsl.findAll(pageable) } returns postPage

        // when
        val response = postService.getPublicPosts(pageable)

        // then
        assertNotNull(response)
        assertThat(response.data.size).isEqualTo(2)
        assertThat(response.currentPage).isEqualTo(0)
        assertThat(response.pageSize).isEqualTo(2)
        assertThat(response.totalElements).isEqualTo(3)
        assertThat(response.hasNext).isTrue()
        assertThat(response.data[0].userName).isEqualTo("user1")
        assertThat(response.data[0].caption).isEqualTo("post1 caption")
    }

    private fun setPostId(
        post: Post,
        id: Long,
    ) {
        // reflection을 사용하여 Post 객체의 id 필드에 값을 설정합니다.
        val idField = Post::class.java.superclass.getDeclaredField("id")
        idField.isAccessible = true
        idField.set(post, id)
    }
}
