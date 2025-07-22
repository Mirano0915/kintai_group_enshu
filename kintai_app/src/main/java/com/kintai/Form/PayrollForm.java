package com.kintai.Form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayrollForm {

	@NotBlank(message = "名前を入力してください")
	@Size(max = 20, message = "名前は20文字以内で入力してください")
	private String name;

	@NotNull(message = "時給を入力してください")
	@Min(value = 900, message = "時給が低すぎます")
    @Max(value = 2000, message = "時給が高すぎます")
	private Integer hourlyWage;

}
