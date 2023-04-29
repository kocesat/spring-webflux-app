package com.kocesat.reaactiveref.service;

import com.kocesat.reaactiveref.client.student.StudentClient;
import com.kocesat.reaactiveref.client.student.StudentServerRequest;
import com.kocesat.reaactiveref.model.Student;
import com.kocesat.reaactiveref.util.web.ListUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
  private final StudentClient studentClient;

  public Mono<List<Student>> getStudents(int page, int pageSize) {
    StudentServerRequest serverRequest = StudentServerRequest.builder()
      .lastId(0L)
      .build();
    List<Student> students = new ArrayList<>();
    int maxCount = page * pageSize;
    return callStudentApiRecursively(serverRequest, students, maxCount)
      .flatMap(allStudents -> Mono.just(ListUtil.paginate(allStudents, page, pageSize)));
  }

  public Mono<List<Student>> callStudentApiRecursively(
    StudentServerRequest serverRequest,
    List<Student> students,
    int maxCount
  ) {
    return studentClient.getStudents(serverRequest)
      .doOnNext(x -> log.info(String.format(
        "Calling api for listing students for lastId: %s.",
        serverRequest.getLastId()
      )))
      .flatMap(serverResponse -> {
        students.addAll(serverResponse.getStudentList());
        if (serverResponse.isHasMore() && students.size() < maxCount) {
          serverRequest.setLastId(lastId(serverResponse.getStudentList()));
          return callStudentApiRecursively(serverRequest, students, maxCount);
        }
        return Mono.just(students);
      });
  }


  private static Long lastId(List<Student> students) {
    return students.get(ListUtil.lastIndex(students)).getId();
  }
}
