package webserver;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import org.apache.commons.beanutils.ConvertUtils;
import org.simpleframework.http.Request;
import org.simpleframework.http.Status;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerServer;
import org.simpleframework.transport.connect.SocketConnection;
import webserver.annotations.GET;
import webserver.annotations.POST;
import webserver.annotations.PathParam;
import webserver.annotations.QueryParam;
import webserver.errors.NotFoundException;
import webserver.internal.PathMatcher;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

//    config.getProperties().put(PROPERTY_CONTAINER_REQUEST_FILTERS, GZIPContentEncodingFilter.class);
//    config.getProperties().put(PROPERTY_CONTAINER_RESPONSE_FILTERS, GZIPContentEncodingFilter.class);
public class FastHttpServer implements Container {
  private final Object[] resources;
  private final Gson gson;
  private SocketConnection server;

  public FastHttpServer(Object... resources) {
    this.resources = resources;
    this.gson = new Gson();
  }

  public void start(int port) throws IOException {
    server = new SocketConnection(new ContainerServer(this));
    server.connect(new InetSocketAddress(port));
  }

  public void close() throws IOException {
    server.close();
  }

  @Override
  public void handle(Request request, org.simpleframework.http.Response response) {
    String path = request.getPath().getPath();
    String httpMethod = request.getMethod();

    try {
      for (Object resource : resources) {
        if ("GET".equals(httpMethod)) {
          Class<?> type = resource.getClass();
          for (Method method : type.getDeclaredMethods()) {
            if (get(path, resource, method, request, response)) {
              return;
            }
          }
        } else if ("POST".equals(httpMethod)) {
          Class<?> type = resource.getClass();
          for (Method method : type.getDeclaredMethods()) {
            if (post(path, resource, method, request, response)) {
              return;
            }
          }
        }
      }

      response.setCode(Status.NOT_FOUND.code);
    } catch (NotFoundException e) {
      response.setCode(Status.NOT_FOUND.code);
    } catch (Exception e) {
      response.setCode(Status.INTERNAL_SERVER_ERROR.code);
      System.err.println(path + " - " + e);
    } finally {
      try {
        response.close();
      } catch (IOException e) {
        // Ignore
      }
    }
  }

  private boolean get(String path, Object resource, Method method, Request request, org.simpleframework.http.Response response) throws IllegalAccessException, InvocationTargetException, IOException {
    GET annotation = method.getAnnotation(GET.class);
    if (annotation == null) {
      return false;
    }
    return respond(annotation.path(), annotation.produces(), path, resource, method, request, response);
  }

  private boolean post(String path, Object resource, Method method, Request request, org.simpleframework.http.Response response) throws IllegalAccessException, InvocationTargetException, IOException {
    POST annotation = method.getAnnotation(POST.class);
    if (annotation == null) {
      return false;
    }

    return respond(annotation.path(), annotation.produces(), path, resource, method, request, response);
  }

  private boolean respond(String expectedPath, String contentType, String path, Object resource, Method method, Request request, org.simpleframework.http.Response response) throws IllegalAccessException, InvocationTargetException, IOException {
    PathMatcher.Result matcher = new PathMatcher(expectedPath).match(path);
    if (!matcher.matches) {
      return false;
    }

    Object body = invokeMethod(resource, method, matcher, request);
    if (body instanceof Response) {
      body = ((Response) body).getEntity();
    }

    sendResponse(body, contentType, response);
    return true;
  }

  private Object invokeMethod(Object resource, Method method, PathMatcher.Result matcher, Request request) throws IllegalAccessException, InvocationTargetException {
    Annotation[][] annotations = method.getParameterAnnotations();
    Class<?>[] types = method.getParameterTypes();
    Object[] arguments = new Object[annotations.length];

    for (int i = 0; i < annotations.length; i++) {
      Annotation annotation = annotations[i][0];

      if (annotation instanceof PathParam) {
        String key = ((PathParam) annotation).value();
        arguments[i] = ConvertUtils.convert(matcher.params.get(key), types[i]);
      } else if (annotation instanceof QueryParam) {
        String key = ((QueryParam) annotation).value();
        arguments[i] = ConvertUtils.convert(request.getQuery().get(key), types[i]);
      }
    }

    return method.invoke(resource, arguments);
  }

  private void sendResponse(Object body, String contentType, org.simpleframework.http.Response response) throws IOException {
    response.setCode(200);
    response.setValue("Content-Type", contentType);

    if (body instanceof Response) {
      ((Response) body).writeHeaders(response);
      if (((Response) body).getEntity() instanceof File) {
        response.setValue("Last-modified", Response.DATE_FORMAT.get().format(((File) ((Response) body).getEntity()).lastModified()));
      }
    }
    if (body instanceof File) {
      response.setValue("Last-modified", Response.DATE_FORMAT.get().format(((File) body).lastModified()));
    }

    writeBody(body, response);
  }

  private void writeBody(Object body, org.simpleframework.http.Response response) throws IOException {
    byte[] bytes = toByteArray(body);

    response.setContentLength(bytes.length);

    OutputStream out = response.getOutputStream();
    out.write(bytes);
    out.flush();
  }

  private byte[] toByteArray(Object body) throws IOException {
    if (null == body) return new byte[0];
    if (body instanceof File) return Files.toByteArray((File) body);
    if (body instanceof byte[]) return (byte[]) body;
    if (body instanceof String) return ((String) body).getBytes(Charsets.UTF_8);

    return gson.toJson(body).getBytes(Charsets.UTF_8);
  }
}