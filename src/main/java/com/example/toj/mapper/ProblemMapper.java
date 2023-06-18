package com.example.toj.mapper;

import com.example.toj.pojo.History;
import com.example.toj.pojo.PassRate;
import com.example.toj.pojo.Problem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProblemMapper {

    @Select("select * from problem")
    List<Problem> getAllProblems();

    @Select("select * from problem where id = #{problemId}")
    Problem getProblem(@Param("problemId") Integer problemId);

    @Select("select * from history where user_id=#{userId} and result=\"通过\"")
    List<History> getUserPassedHistory(@Param("userId") Integer userId);

    @Select("select problem_id, sum(result=\"通过\") / count(*) as pass_rate from history group by problem_id")
    List<PassRate> getAllProblemPassRate();
}
