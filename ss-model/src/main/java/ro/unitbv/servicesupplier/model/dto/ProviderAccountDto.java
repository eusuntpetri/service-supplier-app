package ro.unitbv.servicesupplier.model.dto;

import ro.unitbv.servicesupplier.model.dto.builder.IBuilder;

import java.io.Serializable;

/**
 * Created by Petri on 15-May-16.
 */
public class ProviderAccountDto extends AccountDto implements Serializable {

   private String name;

   private ProviderAccountDto(Builder builder) {
      super(builder);
      this.name = builder.name;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public static class Builder extends AccountDto.Builder<Builder> implements IBuilder<ProviderAccountDto> {

      private String name;

      public Builder withName(String name) {
         this.name = name;
         return this;
      }

      @Override
      public ProviderAccountDto build() {
         return new ProviderAccountDto(this);
      }
   }
}
