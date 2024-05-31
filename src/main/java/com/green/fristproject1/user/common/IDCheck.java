package com.green.fristproject1.user.common;



import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IDCheck {//===========연구중===============
    private static final String ID_REGEX = "^[a-zA-Z0-9]{6,12}$"; // 6~12 글자 영문 대소문자 숫자 0~9 만 가능
    //  "^시작  $"끝? []안에 제한하고 싶은 문자? {}안에 자리수 최소 최대
    private static final String PASSWORD_REGEX =
            "^(?=.*[!@#$%^&*()-_=+\\\\|\\[\\]{};:'\",.<>/?]).{8,20}$\n"; //영문 8~20 글자 특수문자 1개이상 포함,

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\." + //아이디
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";

    String Emi="^" // 정규식 시작 기호
            + "[a-zA-Z0-9_+&*-]+" //영어 대소문자, 숫자 전체와 특수문자(_,+,&,*,-)중 1개의 문자를 1번이상 반복(+)하시오.
            + "(?:\\.[a-zA-Z0-9_+&*-]+)*" // (\\.가 마침표 찍기?? [위와 같음]을 1번 이상 반복하는 것(+)을 0번 이상 반복(*)하시오.
                //아이디
            + "@" // @를 쓰시오
                //  @
            + "(?:[a-zA-Z0-9-]+\\.)+" //영어 대소문자, 숫자와 특수기호(-)를 1번이상 반복(+)하고 마침표를 찍는 것을 1번 이상 반복(+)하시오.
                // naver  .
            + "[a-zA-Z]{2,7}" //영어 대소문자를 최소 2회 최대 7회 반복하시오.
                // net, com
            + "$"; // 정규식 마침 기호
    // 1. \\.는 점을 찍는 기호인가? 이스케이프 문자? 2. "(?:" 의 의미가 뭔지 모르겠다)

    /* 내 나름 정리

    ^ : 시작 기호
    & : 끝 기호
    [] : 안의 1 문자 중 1개
    () : 그룹
    + : 1회 이상 반복
    * : 0번 이상 반복
    {m,n} : 최소 m회, 최대 n회 반복

     */

    public static boolean isValidEmail(String email) { //Validation
        Pattern pattern = Pattern.compile(EMAIL_REGEX); //컴파일: 사람 말을 컴퓨터 말로
        Matcher matcher = pattern.matcher(email);
        // 저급언어를 가진 pattern객체의 matcher메소드에 String값을 넣어 해당 정규식과 일치하는지 확인
        // Matcher 객체에 저장
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) { //Validation
        Pattern pattern=Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidId(String id) { //Validation
        Pattern pattern = Pattern.compile(ID_REGEX);
        Matcher matcher = pattern.matcher(id);
        return matcher.matches();
    }

}           //wltngus200@naver.com

//해야 할 일 : 정규식 해석
//페이지 VS 메소드 => 프로필 변경정도는 본인 인증 없어도 괜찮을 거 같고
//임시 비번 VS 비밀번호 찾기 차이







//남들은 뭔 기능을 만들지
//5번 로그인 실패 하면 차단 되는 기능
