package com.daemawiki.daemawiki.interfaces.document.dto.request;

import com.daemawiki.daemawiki.interfaces.document.dto.DocumentElementDtos;

import java.util.Set;

/**
 * <p>
 * 새 문서를 생성하기 위한 데이터 전송 객체. <br/>
 * 이 요청에는 문서를 생성하는 데 필요한 제목, 유형, 카테고리 등의 정보가 포함됩니다.
 * <br/><br/>
 * 해당 record 클래스는 HTTP request Body 뿐만이 아니라, Application layer에서도 사용되는 범용적인 객체입니다.
 * </p>
 *
 * <p>
 * 사용 예제 :
 * <pre>
 * {@code
 * var request = new CreateDocumentRequest(
 *     new DocumentElementDtos.TitleDto(
 *          "김승원 메인 타이틀",
 *          "김승원 서브 타이틀"
 *     ),
 *     DocumentElementDtos.TypeDto.STUDENT,
 *     Set.of("학생", "9기", "백엔드")
 * );
 * }
 * </pre>
 * </p>
 *
 * @param title    문서의 제목 객체로 메인, 서브 타이틀로 정의됩니다. {@link DocumentElementDtos.TitleDto}
 * @param type     문서의 유형으로 미리 정의된 열거형 클래스 {@link DocumentElementDtos.TypeDto}로 표현됩니다.
 * @param category 문서가 속하는 카테고리로, 문자열 집합(Set)입니다.
 */
public record CreateDocumentRequest(
        DocumentElementDtos.TitleDto title,
        DocumentElementDtos.TypeDto type,
        Set<String> category
) {
}
