package ru.kpfu.itis.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Сервлет, обрабатывающий запросы, адресованные к корню сайта
 * сервлет получает из сессии (которая приходит в сервлет как часть запроса) данные клиента
 * и передает их в шаблонизатор через атрибуты запроса
 *
 * ниже приведен фрагмент файла web.xml (дескриптора развертывания веб-приложения), это альтернатива
 * аннотации @WebServlet("/"). Можно использовать что-то одно
 **/
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Запрашиваем сессию, запрещая создавать новую в случае отсутствия
        // т.к. на этот момент клиент уже должен был успешно пройти аутентификацию (все запросы для проверки перехватывает фильтр),
        // то сессия должна существовать (по логике КОНКРЕТНО ЭТОГО приложения)
        HttpSession session = request.getSession(false);
        String clientName = (String)session.getAttribute("clientname");
        Long clientId = (Long)session.getAttribute("clientid");

        // кладем в атрибуты запроса данные, эти атрибуты будут обработаны шаблонизатором
        request.setAttribute("clientid", clientId);
        request.setAttribute("clientname", clientName);
        request.setAttribute("hello", "Hello for freemarker!");

        //Передаем управление диспетчеру , говоря, что требуется обработать сервлет по пути
        // index.ftl
        request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request, response);
    }
}