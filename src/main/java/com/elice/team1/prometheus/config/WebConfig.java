package com.elice.team1.prometheus.config;

import com.elice.team1.prometheus.item.property.FileUploadProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final FileUploadProperties fileUploadProperties;
    //뷰 컨트롤러는 잘 모르겠음
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        WebMvcConfigurer.super.addViewControllers(registry);
//    }


    //이 부분이 없으면 사진 올렸을때 새로고침 안하면 사진 못불러옴
    private String dir;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        dir = fileUploadProperties.getRootPath()+ fileUploadProperties.getDir();

        //클라이언트가 파일에 접근하기 위한 url
        //TODO OS별로 경로가 다른건지 확인해볼 것
        if(fileUploadProperties.getEnvName().equals("dev")){
            registry.addResourceHandler(fileUploadProperties.getImagePath()+"**")
                    .addResourceLocations("file:/"+dir);
        }else{
            registry.addResourceHandler(fileUploadProperties.getImagePath()+"**")
                    .addResourceLocations("file://"+dir);
        }
    }
}
