/*
 * Copyright (c) 2012-2018 University Corporation for Atmospheric Research/Unidata
 * See LICENSE for license information.
 */

package edu.ucar.unidata.rosetta.converters.batch;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.ucar.unidata.rosetta.util.test.category.NeedsLocalServer;
import edu.ucar.unidata.rosetta.util.test.util.TestUtils;
import static org.junit.Assert.assertEquals;

/**
 * Test the conversion of an eTUFF file
 */
@Category(NeedsLocalServer.class)
public class TestTagUniversalFileFormatBatchProcess {

  private static String etuffDir = String.join(File.separator, "conversions", "TagUniversalFileFormat");
  private static String etuffFileTld = TestUtils.getTestDataDirStr() + etuffDir;
  private static String uploadFile = String.join(File.separator, etuffFileTld, "test_simple_api.zip");

  private static String topLevelZip = "/test_simple_api";

  String batchProcessUrl = TestUtils.getTestServerUrl() + "/batchProcess";

  @Before
  public void makeUploadZip() throws IOException {
    Map<String, String> env = new HashMap<>();
    env.put("create", "true");
    // locate file system by using the syntax
    // defined in java.net.JarURLConnection
    String uploadFileUri = Paths.get(uploadFile).toUri().toString();
    URI uri = URI.create("jar:" + uploadFileUri);
    try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
      // List<String> filesToAdd = Arrays.asList("TagDataFlatFileExample.txt", "rosetta.template");
      List<Path> filesToAdd = new ArrayList<>();
      filesToAdd.add(Paths.get(etuffFileTld, "eTUFF-tuna-590051-small.txt"));
      filesToAdd.add(Paths.get(etuffFileTld, topLevelZip, "rosetta.template"));
      for (Path externalTxtFile : filesToAdd) {
        Path zipTopLevelDir = zipfs.getPath(topLevelZip);
        Files.createDirectories(zipTopLevelDir);
        Path pathInZipfile = zipTopLevelDir.resolve(externalTxtFile.getFileName().toString());
        Files.copy(externalTxtFile, pathInZipfile, StandardCopyOption.REPLACE_EXISTING);
      }
    }
  }

  @Test
  public void testBatchProcessEtuff() {
    try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

      File fileToUpload = new File(uploadFile);
      FileBody fileBody = new FileBody(fileToUpload);

      HttpPost post = new HttpPost(batchProcessUrl);

      MultipartEntityBuilder builder = MultipartEntityBuilder.create();
      builder.addPart("batchZipFile", fileBody);
      HttpEntity entity = builder.build();

      post.setEntity(entity);
      HttpResponse response = httpclient.execute(post);
      StatusLine statusLine = response.getStatusLine();
      assert statusLine.getStatusCode() == 200;

      HttpEntity responseEntity = response.getEntity();
      long contentLength = responseEntity.getContentLength();

      Header[] clHeader = response.getHeaders("Content-Length");
      assert clHeader.length == 1;
      assertEquals(Long.valueOf(clHeader[0].getValue()), Long.valueOf(contentLength));

      Header[] ctHeader = response.getHeaders("Content-Type");
      assert ctHeader.length == 1;
      assertEquals("application/zip", ctHeader[0].getValue());

    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @After
  public void cleanup() {
    File zipFile = Paths.get(uploadFile).toFile();
    if (zipFile.exists()) {
      zipFile.delete();
    }

  }
}
