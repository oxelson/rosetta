/*
 * Copyright (c) 2012-2020 University Corporation for Atmospheric Research/Unidata.
 * See LICENSE for license information.
 */

package edu.ucar.unidata.rosetta.domain.wizard;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Form-backing object for the wizard to collect a collection of uploaded files. Used for dynamic form binding in Spring
 * to collect multiple uploaded file objects. The term 'Cmd' in the name refers to the command object used in form data
 * binding. Lombok automatic generation of getters, setters, equals, hashCode and toString.
 *
 * @author oxelson
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UploadedFileCmd extends WizardData {

  private String dataFileType;
  private List<UploadedFile> uploadedFiles = new ArrayList<>(3);

  public UploadedFileCmd() {
    // Data file.
    UploadedFile dataFile = new UploadedFile();
    dataFile.setFileType(UploadedFile.FileType.DATA);
    dataFile.setDescription("The file containing the ASCII data you wish to convert.");
    dataFile.setRequired(true);
    addToUploadedFiles(dataFile);

    // Positional file.
    UploadedFile positionalFile = new UploadedFile();
    positionalFile.setFileType(UploadedFile.FileType.POSITIONAL);
    positionalFile.setDescription(
        "An optional file containing positional data " + "corresponding to the data contained in the data file.");
    positionalFile.setRequired(false);
    addToUploadedFiles(positionalFile);

    // Template file.
    UploadedFile templateFile = new UploadedFile();
    templateFile.setFileType(UploadedFile.FileType.TEMPLATE);
    templateFile.setDescription("A Rosetta template file used for converting the data file.");
    templateFile.setRequired(false);
    addToUploadedFiles(templateFile);
  }

  public UploadedFileCmd(List<UploadedFile> uploadedFiles) {
    this();
    for (UploadedFile uploadedFile : uploadedFiles) {
      replaceUploadedFile(uploadedFile);
    }
  }

  /**
   * Adds an uploaded file to the list.
   *
   * @param uploadedFile The uploaded file to add.
   */
  public void addToUploadedFiles(UploadedFile uploadedFile) {
    this.uploadedFiles.add(uploadedFile);
  }

  /**
   * Updates one of the existing files in the uploaded files.
   *
   * @param replacementFile The file to replace.
   */
  public void replaceUploadedFile(UploadedFile replacementFile) {
    for (UploadedFile uploadedFile : this.uploadedFiles) {
      if (replacementFile.getFileType().equals(uploadedFile.getFileType())) {
        int index = uploadedFiles.indexOf(uploadedFile);
        this.uploadedFiles.set(index, replacementFile);
      }
    }
  }
}
