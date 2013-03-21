package webserver;

import com.google.common.collect.Maps;
import org.simpleframework.http.Status;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Map;

public class Response {
  private static final long ONE_YEAR = 1000L * 3600 * 24 * 365;

  public static ThreadLocal<SimpleDateFormat> DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() {
    @Override
    protected SimpleDateFormat initialValue() {
      return new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss zzz");
    }
  };

  private Status status = Status.OK;
  private Object entity = "";
  private Long lastModified;
  private Long expires;
  private Map<String, String> headers = Maps.newLinkedHashMap();

  public Response(Object entity) {
    this.status = Status.OK;
    this.entity = entity;
  }

  public static Response ok(Object entity) {
    return new Response(entity);
  }

  public Response lastModified(File file) {
    return lastModified(file.lastModified());
  }

  public Response lastModified(long date) {
    this.lastModified = date;
    this.expires = date + ONE_YEAR;
    header("Cache-Control", "public");
    return this;
  }

  public Response header(String key, String value) {
    headers.put(key, value);
    return this;
  }

  public Response setStatus(Status status) {
    this.status = status;
    return this;
  }

  public Object getEntity() {
    return entity;
  }

  void writeHeaders(org.simpleframework.http.Response response) {
    response.setCode(status.code);

    if (expires != null) {
      response.setValue("Expires", DATE_FORMAT.get().format(expires));
    }

    if (lastModified != null) {
      response.setValue("Last-modified", DATE_FORMAT.get().format(expires));
    }

    for (Map.Entry<String, String> header : headers.entrySet()) {
      response.setValue(header.getKey(), header.getValue());
    }
  }
}
