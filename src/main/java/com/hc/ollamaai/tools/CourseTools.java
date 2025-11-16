package com.hc.ollamaai.tools;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.hc.ollamaai.pojo.entity.Course;
import com.hc.ollamaai.pojo.entity.CourseReservation;
import com.hc.ollamaai.pojo.entity.School;
import com.hc.ollamaai.pojo.query.CourseQuery;
import com.hc.ollamaai.service.ICourseReservationService;
import com.hc.ollamaai.service.ICourseService;
import com.hc.ollamaai.service.ISchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

// function calling工具
@Component
@RequiredArgsConstructor
public class CourseTools {
    private final ICourseService courseService;
    private final ISchoolService schoolService;
    private final ICourseReservationService courseReservationService;

    @Tool(description = "查询所有课程信息")
    public List<Course> queryCourses(@ToolParam(description = "查询条件", required = false) CourseQuery query) {
        if (query == null) return List.of();
        QueryChainWrapper<Course> wrapper = courseService.query()
                .eq(query.getEdu() != null, "edu", query.getEdu())
                .eq(query.getType() != null, "type", query.getType());
        if (query.getSorts() != null && !query.getSorts().isEmpty()) {
            for (CourseQuery.Sort sort : query.getSorts()) {
                if (sort.getField() != null && sort.getAsc() != null) {
                    wrapper.orderBy(true, sort.getAsc(), sort.getField());
                }
            }
        }
        return wrapper.list();
    }

    @Tool(description = "查询所有校区信息")
    public List<School> querySchools() {
        return schoolService.list();
    }

    @Tool(description = "生成预约信息,回显预约单号")
    public Integer createCourseReservation(
            @ToolParam(description = "预约课程") Course course,
            @ToolParam(description = "学生姓名") String studentName,
            @ToolParam(description = "联系方式") String contactInfo,
            @ToolParam(description = "预约校区") String school,
            @ToolParam(description = "备注", required = false) String remark
            ){
        CourseReservation reservation = new CourseReservation();
        reservation.setCourse(course.getName());
        reservation.setStudentName(studentName);
        reservation.setContactInfo(contactInfo);
        reservation.setSchool(school);
        reservation.setRemark(remark);
        courseReservationService.save(reservation);
        return reservation.getId();
    }
}
