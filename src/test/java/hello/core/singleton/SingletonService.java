package hello.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService() { // private 생성자를 만들어놓으면, 외부에서 객체생성을 못하게 된다.
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
// 이렇게 되면 SingletonService 객체를 외부에서 생성하지 못하고,
// SingletonService 객체를 사용하고 싶으면 getInstance() 메서드를 통해서만 꺼내올 수 있다.