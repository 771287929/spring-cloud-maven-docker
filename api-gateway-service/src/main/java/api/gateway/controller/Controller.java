package api.gateway.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.gateway.command.CommandHttpCall;
import api.gateway.service.UserService;
import api.gateway.util.Constant;
import rx.Observable;
import rx.Observer;

@RestController
public class Controller {
	
	@Autowired
	UserService userService;

    @RequestMapping("/google")
    public String getGoogle(){
        return new CommandHttpCall("http://www.google.com",true).execute();
    }

    /*@RequestMapping("/observe")
    public void getObserve() throws InterruptedException {
        Observable<String> productCall = new CommandHttpCall("http://localhost:8091/product").observe();
        Observable<String> orderCall = new CommandHttpCall("http://localhost:8091/order").observe();
        Observable<String> cartCall = new CommandHttpCall("http://localhost:8091/cart").observe();

        List<Observable<String>> result = new ArrayList<>();
        result.add(productCall);
        result.add(orderCall);
        Observable.merge(result).subscribe(new Observer<String>() {

            @Override
            public void onCompleted() {
                System.out.println("product&order call complete");
                cartCall.subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("cart call complete");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String v) {
                        System.out.println("onNext: " + v);
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String v) {
                System.out.println("onNext: " + v);
            }

        });

    }
*/
    @RequestMapping("/show")
    public String getFuture() throws InterruptedException {
        Future<String> userSyncCall = new CommandHttpCall("http://192.168.99.100:8090/users",true).queue();

        try {
            String user = userSyncCall.get();
            System.out.println("sync get user" + user);
            Future<String> countriesSyncCall = new CommandHttpCall("http://192.168.99.100:8089/",true).queue();
            String result=countriesSyncCall.get();
            System.out.println("sync get countries" + result);
            return result;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }

    }
    
    @RequestMapping("/showui1")
    public String getFutureUi1() throws InterruptedException {

        try {
            Future<String> countriesSyncCall = new CommandHttpCall(Constant.SERVICE_UI_NAME,true).queue();
            String result=countriesSyncCall.get();
            System.out.println("sync get countries" + result);
            return result;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }

    }
    @RequestMapping("/showuser1")
    public String getFutureUser1() throws InterruptedException {

    	Future<String> userSyncCall = new CommandHttpCall(Constant.SERVICE_NAME+"user",true).queue();
        try {
            String user = userSyncCall.get();
            return user;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }

    }
   
    @RequestMapping("/showuser2")
    public String getFutureUser2() throws InterruptedException {
        try {
            String user = userService.readUserInfo();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
