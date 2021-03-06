package com.kaishengit.controller;

import com.kaishengit.client.MovieServiceClient;
import com.kaishengit.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private MovieService movieService;

    /*以下而注解为Ribbon loadBalancerClient*/
   /* @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;*/

//    @Autowired
//    private MovieServiceClient movieServiceClient;

    @GetMapping("/buy/movie/{id}")
    public  String buyMovie(@PathVariable Integer id){

        return movieService.getMovieNameById(id);

          //服务消费feign
          //return movieServiceClient.getMovieName(id);

        //服务消费-Ribbon
//        String url = "http://MOVIE-SERVICE-PROVIDER/movie/"+id;
//        return restTemplate.getForObject(url,String.class);

        //服务消费loadBalancerClient
//        ServiceInstance serviceInstance = loadBalancerClient.choose("MOVIE-SERVICE-PROVIDER");
//        System.out.println("uri" + serviceInstance.getUri());
//        System.out.println("serviceId" + serviceInstance.getServiceId());
//        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/movie/" + id;
//        return restTemplate.getForObject(url,String.class);
    }

}
