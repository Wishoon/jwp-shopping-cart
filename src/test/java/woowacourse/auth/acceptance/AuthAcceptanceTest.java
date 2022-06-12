package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.fixture.shoppingcart.TCustomer.ROOKIE;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.auth.application.dto.TokenResponse;
import woowacourse.global.exception.ErrorResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("회원은 이메일, 비밀번호를 통해서 로그인을 하면 상태코드 200 Ok와 토큰을 반환한다.")
    void login() {
        // given
        ROOKIE.signUp();

        // when
        TokenResponse response = ROOKIE.signIn();

        // then
        assertThat(response.getAccessToken()).isNotNull();
    }

    @Test
    @DisplayName("회원이 잘못된 이메일, 비밀번호를 통해서 로그인을 하면 상태코드 400 bad request와 에러 메시지를 반환한다.")
    void failedLogin() {
        // given
        ROOKIE.signUp();

        // when
        ErrorResponse response = ROOKIE.signInFailed();

        // then
        assertThat(response.getMessage()).isEqualTo("[ERROR] 존재하지 않는 사용자 입니다.");
    }

    @Test
    @DisplayName("유효하지 않은 토큰으로 요청을 할 경우 상태코드 400과 bad request와 에러 메시지를 반환한다.")
    void wrongToken() {
        // given
        ROOKIE.signUp();

        // when
        ErrorResponse response = ROOKIE.signInWrongToken();

        // then
        assertThat(response.getMessage()).isEqualTo("알 수 없는 에러가 발생했습니다.");
    }
}
