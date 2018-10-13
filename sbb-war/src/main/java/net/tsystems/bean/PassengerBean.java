package net.tsystems.bean;

import net.tsystems.service.PassengerService;
import net.tsystems.serviceobject.PassengerSO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Date;


@Component
@ManagedBean(name = "passengerBean")
@SessionScoped
@EnableTransactionManagement(proxyTargetClass = true)
public class PassengerBean implements Serializable{

    @Autowired
    @Qualifier("passengerService")
    private PassengerService passengerService;

    /*private PassengerBeanMapper mapper;*/

    private String firstName;
    private String lastName;
    private Date birthday;
    private int searchId;

    public void getPassengerById(){
        //didn't work with searchId = 1 by default either
        PassengerSO p = passengerService.getPassenger(searchId);
        if (p != null)
            firstName = p.getFirstName();
    }

    public int getSearchId() {
        return searchId;
    }

    public void setSearchId(int searchId) {
        this.searchId = searchId;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
