package com.daemawiki.daemawiki.global.util.versioncontrol

import org.springframework.stereotype.Component

@Component
class DiffUtilsImpl : DiffUtils {

    override fun getUpdates(origin: String, modified: String): Updates {
        val updates = Updates(
            emptyMap(),
            emptyMap()
        )

        // 원본 줄 단위 구분
        val originLines = origin.split("\n".toRegex()).toList()
        // 수정본 줄 단위 구분
        val modifiedVersionLines = modified.split("\n".toRegex()).toList()

        // 현재까지 읽어들인 줄 수. 정확히는 변경 사항 추적이 끝난 줄 수
        var readOriginLine = 0;
        var readModifyVersionLine = 0;

        // 변경사항 탐색 시작
        while (true) {

            // 둘이 차이점이 없는, 변경 사항이 없는 줄일 경우
            if (originLines.getOrNull(readOriginLine) == modifiedVersionLines.getOrNull(readModifyVersionLine)) {
                readOriginLine++
                readModifyVersionLine++
                continue
            }

            // 줄이 다르다면 -> 변경 사항이 있다면

            // 추가가 일어났다고 가정하고 수정본 탐색 시작
            // 만약 원본의 n번 줄과 n+1번 줄, 수정본의 n번 줄과 n+1+m번 줄이 각각 같은 경우,
            // n번 줄과 n+1번 줄 사이에 m 줄 만큼의 추가가 일어난 것
            for (readLine in (readModifyVersionLine + 1)..originLines.size) {

                // 수정본 탐색 도중 끝에 다다랐을 경우
                // 즉, 원본에서 삭제가 일어난 경우
                if (modifiedVersionLines.getOrNull(readLine) == null) {
                    break
                }

                // 추가가 일어난 게 맞을 경우
                if (originLines.getOrNull(readOriginLine + 1) == modifiedVersionLines.getOrNull(readLine)) {

                    // TODO("추가된 것 저장하는 로직 추가")

                    continue
                }

            }

            // TODO("삭제가 일어났을 경우 탐색 알고리즘")

        }

        return updates
    }
}