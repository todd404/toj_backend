package com.example.toj.mapper;

import com.example.toj.pojo.History;
import com.example.toj.pojo.PassRate;
import com.example.toj.pojo.Problem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProblemMapper {

    @Select("select * from problem where id > 0")
    List<Problem> queryAllProblems();

    @Select("select * from problem where id = #{problemId} and id > 0")
    Problem queryProblem(@Param("problemId") Integer problemId);

    @Select("select * from history where user_id=#{userId} and result=\"通过\" and id > 0")
    List<History> queryUserPassedHistory(@Param("userId") Integer userId);

    @Select("select problem_id, sum(result=\"通过\") / count(*) as pass_rate from history group by problem_id")
    List<PassRate> queryAllProblemPassRate();

    @Select("""
            select id, user_id, problem_id, language, execute_time, memory, result, created_at from history
            where problem_id = #{problemId} and user_id = #{userId}
            """)
    List<History> querySubmitHistory(@Param("problemId") Integer problemId,
                                     @Param("userId") Integer userId);

    @Insert("insert into problem (title, content, difficulty) values (#{title}, #{content}, #{difficulty})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertProblem(Problem problem);

    @Insert("""
        insert into history (user_id, problem_id, language, code, execute_time, memory, result)
        values (#{userId}, #{problemId}, #{language}, #{code}, #{executeTime}, #{memory}, #{result})
        """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertHistory(History history);

    @Update("update problem set title=#{title}, content=#{content}, difficulty=#{difficulty} where id = #{id}")
    Integer updateProblem(Problem problem);

    @Update("update history set result=#{result}, execute_time=#{executeTime}, memory=#{memory} where id = #{id}")
    Integer updateHistory(History history);

    @Delete("delete from problem where id=#{id}")
    Integer deleteProblem(@Param("id") Integer id);
}
