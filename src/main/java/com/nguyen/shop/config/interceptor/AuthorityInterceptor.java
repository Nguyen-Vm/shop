package com.nguyen.shop.config.interceptor;

import com.google.common.collect.Maps;
import com.nguyen.shop.common.ServerResponse;
import com.nguyen.shop.common.custom.Const;
import com.nguyen.shop.pojo.User;
import com.nguyen.shop.utils.CookieUtil;
import com.nguyen.shop.utils.JsonUtil;
import com.nguyen.shop.utils.RedisSharedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * @author RWM
 * @date 2018/3/14
 *
 * @description 管理员权限校验拦截器
 */
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object handler) throws Exception {
        log.info("preHandle");
        //请求中Controller中的方法名
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        //解析HandlerMethod
        String methodName = handlerMethod.getMethod().getName();
        String className = handlerMethod.getBean().getClass().getSimpleName();


        //解析参数，具体的参数key以及value是什么，我们打印日志
        StringBuffer requestParamsBuffer = new StringBuffer();
        Map paramMap = httpServletRequest.getParameterMap();
        Iterator iterator = paramMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            String mapKey = (String) entry.getKey();
            String mapValue = StringUtils.EMPTY;

            Object obj = entry.getValue();
            if (obj instanceof String[]){
                String[] strings = (String[]) obj;
                mapValue = Arrays.toString(strings);
            }
            requestParamsBuffer.append(mapKey).append("=").append(mapValue);
        }

        if (StringUtils.equals(className, "ProductManageController") && StringUtils.equals(methodName, "uploadOss")) {
            log.info("权限拦截器拦截到请求，className:{}, methodName:{}", className, methodName);
            return true;
        }

        if (StringUtils.equals(className, "UserManageController") && StringUtils.equals(methodName, "login")){
            //如果拦截到登录请求，不打印参数，因为参数里面有密码，全部会打印到日志中，防止密码泄露
            log.info("权限拦截器拦截到请求，className:{}, methodName:{}", className, methodName);
            return true;
        }

        //拦截到请求,打印参数
        log.info("权限拦截器拦截到请求,className:{},methodName:{},param:{}", className, methodName, requestParamsBuffer.toString());


        User user = null;
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isNotEmpty(loginToken)){
            String userJsonStr = RedisSharedPoolUtil.get(loginToken);
            user = JsonUtil.string2Obj(userJsonStr, User.class);
        }

        if (user == null || (user.getRole().intValue() != Const.Role.ROLE_ADMIN)){
            //这里要添加reset，否则报异常 getWriter() has already been called for this response
            httpServletResponse.reset();
            //这里要设置编码，否则会乱码
            httpServletResponse.setCharacterEncoding("UTF-8");
            //这里要设置返回值得类型因为全部是json接口
            httpServletResponse.setContentType("application/json;charset=UTF-8");

            PrintWriter out = httpServletResponse.getWriter();

            //上传由于富文本的控件要求，要特殊处理返回值，这里面区分是否登录以及是否有权限
            if (user == null){
                if (StringUtils.equals(className, "ProductManageController") && StringUtils.equals(methodName, "richtextImgUpload")){
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success",false);
                    resultMap.put("msg","请登录管理员");
                    out.print(JsonUtil.obj2String(resultMap));
                }else {
                    out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截，用户未登录")));
                }
            }else {
                if (StringUtils.equals(className, "ProductManageController") && StringUtils.equals(methodName, "richtextImgUpload")){
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success",false);
                    resultMap.put("msg","无权限操作");
                    out.print(JsonUtil.obj2String(resultMap));
                }else {
                    out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截，无权限操作")));
                }
            }
            out.flush();
            out.close();

            //返回false,拦截该请求,即不会调用controller里的方法
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("afterCompletion");
    }
}
