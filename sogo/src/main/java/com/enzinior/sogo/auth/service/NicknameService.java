package com.enzinior.sogo.auth.service;

import com.enzinior.sogo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class NicknameService {
    private static final String[] PREFIX = {
            "날쌘", "빠른", "느긋한", "귀여운", "용감한", "지혜로운", "강인한", "밝은", "행복한", "슬기로운",
            "조용한", "활기찬", "평화로운", "든든한", "호기심 많은", "재치있는", "사랑스러운", "장난스러운", "씩씩한", "날렵한",
            "상냥한", "푸른", "붉은", "노란", "초록의", "검은", "흰", "웃는", "깜찍한", "낙천적인",
            "똑똑한", "순수한", "차분한", "냉정한", "포근한", "달콤한", "부드러운", "강력한", "신비로운", "차가운",
            "따뜻한", "친절한", "어두운", "맑은", "힘찬", "열정적인", "신속한", "재빠른", "경쾌한", "생동감 있는",
            "고요한", "활발한", "능숙한", "유연한", "고급스러운", "순발력 있는", "반짝이는", "번쩍이는", "빛나는", "숨가쁜",
            "눈부신", "눈빛이 강한", "노련한", "침착한", "능숙한", "신중한", "마음이 넓은", "자비로운", "착한", "용맹스러운",
            "두려움을 모르는", "거침없는", "단호한", "은은한", "소박한", "우아한", "차분한", "활달한", "명랑한", "즐거운",
            "환한", "고요한", "자유로운", "독립적인", "겁이 없는", "도전적인", "열정적인", "낙천적인", "명석한", "귀중한",
            "단단한", "날카로운", "섬세한", "기민한", "날카로운", "민첩한", "용맹한", "진취적인", "결단력 있는"
    };
    private static final String[] ANIMALS = {
            "다람쥐", "호랑이", "독수리", "사자", "토끼", "늑대", "여우", "곰", "원숭이", "코끼리",
            "코알라", "판다", "고양이", "개", "말", "기린", "얼룩말", "펭귄", "상어", "고래",
            "돌고래", "물개", "수달", "하마", "코뿔소", "쥐", "벌새", "앵무새", "부엉이", "올빼미",
            "까마귀", "까치", "까투리", "고슴도치", "뱀", "도마뱀", "개구리", "도토리", "다랑어", "해파리",
            "해마", "고래상어", "바다거북", "갈매기", "해오라기", "두루미", "흰두루미", "제비", "참새", "족제비",
            "스컹크", "사슴", "노루", "표범", "재규어", "치타", "눈표범", "퓨마", "고릴라", "침팬지",
            "오랑우탄", "인간", "바다사자", "사막여우", "사막토끼", "코요테", "북극곰", "북극여우", "재두루미", "까치",
            "물소", "들소", "영양", "산양", "흑곰", "백곰", "라마", "알파카", "미어캣", "치타",
            "사막지렁이", "두더지", "두루미", "두꺼비", "들쥐", "당나귀", "낙타", "새끼고양이", "새끼호랑이", "아기곰",
            "병아리", "새끼사자", "얼룩말", "여우원숭이", "고슴도치", "쥐", "개미핥기", "아르마딜로", "페릿", "흰족제비"
    };
    private static final Random RANDOM = new Random();

    private final UserRepository userRepository;

    public String createNickname() {
        String prefix = PREFIX[RANDOM.nextInt(PREFIX.length)];
        String animal = ANIMALS[RANDOM.nextInt(ANIMALS.length)];
        String number = String.format("%04d", RANDOM.nextInt(10000)); // 0000 ~ 9999 범위의 숫자 생성

        String nickname = prefix + " " + animal + " " + number;

        while (userRepository.existsByNickname(nickname)) {
            prefix = PREFIX[RANDOM.nextInt(PREFIX.length)];
            animal = ANIMALS[RANDOM.nextInt(ANIMALS.length)];
            number = String.format("%04d", RANDOM.nextInt(10000)); // 0000 ~ 9999 범위의 숫자 생성

            nickname = prefix + " " + animal + " " + number;
        }
        // 닉네임 조합
        return nickname;
    }
}
