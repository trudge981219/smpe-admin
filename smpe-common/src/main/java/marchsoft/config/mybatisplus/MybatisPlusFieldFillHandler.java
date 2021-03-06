package marchsoft.config.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import marchsoft.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * description:mybatis plus 自定义自动填充策略
 * TODO:@RenShiWei 2020/12/6 description:根据启动环境判断注入信息是否可以为null
 *
 * @author RenShiWei
 * Date: 2020/7/13 16:13
 **/
@Component
@Slf4j
public class MybatisPlusFieldFillHandler implements MetaObjectHandler {

    /**
     * description:新增数据，填充创建时间和更新时间
     *
     * @author RenShiWei
     * Date: 2020/7/13 16:14
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("Fill field createAT and updateAT by insert...");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createBy", String.class, getUsername());
        this.strictInsertFill(metaObject, "updateBy", String.class, getUsername());
    }

    /**
     * description:更新数据，填充更新时间
     *
     * @author RenShiWei
     * Date: 2020/7/13 16:14
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("Fill field updateAT by update...");
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "updateBy", String.class, getUsername());
    }

    /**
     * description:获取自动注入的用户名username，如果为null，注入null
     *
     * @return /
     * @author RenShiWei
     * Date: 2020/12/5 15:50
     */
    private String getUsername() {
        return SecurityUtils.getCurrentUsernameNoThrow();
    }

}
