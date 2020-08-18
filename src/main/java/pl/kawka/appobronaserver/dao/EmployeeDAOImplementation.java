package pl.kawka.appobronaserver.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.kawka.appobronaserver.model.EmployeeLogin;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class EmployeeDAOImplementation implements EmployeeDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<EmployeeLogin> getPracownikSQL() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<EmployeeLogin> query = currentSession.createQuery("from EmployeeLogin", EmployeeLogin.class);
        List<EmployeeLogin> list = query.getResultList();
        System.out.println(list);

       /* JSONObject json = new JSONObject();
        json.put("login", login.getText());*/

        return list;
    }

    @Override
    public String getLogowanie(EmployeeLogin employeeLoginLogowanie) {

        System.out.println(employeeLoginLogowanie);
        String login1 = employeeLoginLogowanie.getLogin();
        System.out.println(login1); //odczyt loginu z JSONA co przyszedl
        String password1 = employeeLoginLogowanie.getPassword();
        System.out.println(password1); //odczyt hasla z JSONA co przyszedl

        Session currentSession = entityManager.unwrap(Session.class);
        Query<EmployeeLogin> query = currentSession.createQuery("from EmployeeLogin " +
                        "WHERE login='"+login1+"' and password='"+password1+"' ",
                EmployeeLogin.class);
        List<EmployeeLogin> list = query.getResultList();
        System.out.println("Logowanie lista :" + list);

        System.out.println("Status pracownika: " + list.toString().contains("status='pracownik'"));
        System.out.println("Status admina: " + list.toString().contains("status='admin'"));

        if (list.isEmpty()){
            System.out.println("nieudane logowanie");
            return "zle";
        } else if (list.toString().contains("status='pracownik'")){
            System.out.println("udane logowanie pracownika");
            return "OKpracownik";
        }  else if (list.toString().contains("status='admin'")){
        System.out.println("udane logowanie admina");
        return "OKadmin";
        }
        else{
            System.out.println("nieudane logowanie2");
            return "zle2";
        }




    }
}