/*
 * Copyright (c) 2012-2020 University Corporation for Atmospheric Research/Unidata.
 * See LICENSE for license information.
 */

package edu.ucar.unidata.rosetta.domain.wizard;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Form-backing object for the wizard to collect an uploaded file. Lombok automatic generation of noop constructor,
 * getters, setters, equals, hashCode and toString.
 *
 * @author oxelson
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UploadedFile extends WizardData implements Serializable {

  private String description;
  private CommonsMultipartFile file = null;
  private String fileName;
  private FileType fileType;
  private String id;
  private boolean required;

  public UploadedFile(CommonsMultipartFile file, String fileName, FileType fileType) {
    setFile(file);
    setFileName(fileName);
    setFileType(fileType);
  }

  /**
   * Returns whether the file is required to be present during form submission.
   *
   * @return true if required; otherwise false.
   */
  public boolean isRequired() {
    return required;
  }

  public enum FileType {
    DATA, POSITIONAL, TEMPLATE
  }
}
