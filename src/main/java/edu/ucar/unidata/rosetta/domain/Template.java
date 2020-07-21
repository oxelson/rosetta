/*
 * Copyright (c) 2012-2020 University Corporation for Atmospheric Research/Unidata.
 * See LICENSE for license information.
 */

package edu.ucar.unidata.rosetta.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * An object representing a rosetta template. Used for both custom and known file types. As per:
 * https://github.com/Unidata/rosetta/wiki/Rosetta-Template-Attributes
 *
 * Lombok automatic generation of noop constructor, getters, setters, equals, hashCode and toString.
 *
 * @author oxelson
 */
@Data
@NoArgsConstructor
public class Template {

  private String cfType;
  private String community;
  private String creationDate;
  private String delimiter;
  private String format; // required
  private List<RosettaGlobalAttribute> globalMetadata;
  private List<Integer> headerLineNumbers;
  private String platform;
  private String rosettaVersion;
  private String serverId;
  private String templateVersion;
  private List<VariableInfo> variableInfoList;

  /**
   * Update a template with new values found in a second template. Generally, only metadata
   * are updated. Used for overriding global and variable metadata during batch processing.
   *
   * @param updatedTemplate template containing new metadata
   */
  public void update(Template updatedTemplate) {

    List<Integer> headerLineNumbersUpdate = updatedTemplate.getHeaderLineNumbers();
    if (headerLineNumbersUpdate != null) {
      // simple to update - just replace old list with new list
      this.headerLineNumbers = headerLineNumbersUpdate;
    }

    List<RosettaGlobalAttribute> globalMetadataUpdates = updatedTemplate.getGlobalMetadata();
    if (globalMetadataUpdates != null) {
      // A little more complex. We need to go through each new piece of global metadata,
      // search for it by name in the existing list of global metadata, and remove it if it
      // exits. Then, we can merge the new and existing lists of global metadata.
      for (RosettaGlobalAttribute globalMetadataUpdate : globalMetadataUpdates) {
        Predicate<RosettaAttribute> attributePredicate = attr -> attr.getName().equals(globalMetadataUpdate.getName());
        this.globalMetadata.removeIf(attributePredicate);
      }
      this.globalMetadata.addAll(globalMetadataUpdates);
    }

    List<VariableInfo> variableInfoListUpdates = updatedTemplate.getVariableInfoList();
    if (variableInfoListUpdates != null) {
      // There are two cases to handle here:
      // For each piece of new variableInfo, check if the name matches the name of a VariableInfo
      // object in the list. If so, update it using VariableInfo.update(). If the name of the new
      // piece of variableInfo is not in the list, then it is new and can be added to the list.

      // Let's get all of the names of the variableInfo currently held in variableInfoList.
      List<String> names = new ArrayList<>();
      for (VariableInfo variableInfo : variableInfoList) {
        names.add(variableInfo.getName());
      }

      // Now, let's figure out what to do with each new piece of variableInfo.
      for (VariableInfo variableInfoUpdate : variableInfoListUpdates) {
        String updateName = variableInfoUpdate.getName();
        // For each updated variableInfo entry, check if the name already appears in
        // this object's variableInfoList.
        if (names.contains(updateName)) {
          // Name already in list, so update it where it is found.
          int updateIndex = names.indexOf(updateName);
          VariableInfo oldVarInfo = variableInfoList.get(updateIndex);
          // If updated template has colNum set to -1, that means remove the variable.
          if (variableInfoUpdate.getColumnId() == -9) {
            variableInfoList.remove(updateIndex);
          } else {
            oldVarInfo.updateVariableInfo(variableInfoUpdate);
            variableInfoList.set(updateIndex, oldVarInfo);
          }
        } else {
          // This is a new addition - go ahead and add it to the list.
          variableInfoList.add(variableInfoUpdate);
        }
      }
    }
  }
}
