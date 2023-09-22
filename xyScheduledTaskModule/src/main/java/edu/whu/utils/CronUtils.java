package edu.whu.utils;

import edu.whu.exception.CustomerException;
import edu.whu.exception.TaskException;
import edu.whu.model.common.enumerate.ExceptionEnum;
import org.quartz.CronExpression;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Akihabara
 * @version 1.0
 * @description CronUtils: Cron表达式相关工具类
 * @date 2023/9/21 18:51
 */
public class CronUtils {
    /**
     * 检测Cron表达式是否有效
     * @param cronExpression    cron表达式
     * @return                  表达式是否有效
     */
    public static boolean isValid(String cronExpression) {
        return CronExpression.isValidExpression(cronExpression);
    }

    /**
     * 返回一个字符串值, 表示该消息无效Cron表达式给出有效性
     * @param cronExpression    Cron表达式
     * @return                  无效时返回表达式错误描述
     */
    public static String getInvalidMessage(String cronExpression) {
        try {
            new CronExpression(cronExpression);
            return null;
        } catch (ParseException exception) {
            return exception.getMessage();
        }
    }

    /**
     * 获取当前cron表达式的下一次执行时间
     * @param cronExpression    cron表达式
     * @return                  下一次执行时间
     */
    public static Date getNextExecution(String cronExpression) {
        try {
            CronExpression cron = new CronExpression(cronExpression);
            return cron.getNextInvalidTimeAfter(new Date(System.currentTimeMillis()));
        } catch (ParseException e) {
            throw new CustomerException(ExceptionEnum.CRON_INVALID);
        }
    }
}
