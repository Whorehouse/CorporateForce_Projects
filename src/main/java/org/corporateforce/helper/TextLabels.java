package org.corporateforce.helper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TextLabels {

	private static final Map<String, String> LABELS;
	static {
		HashMap<String, String> labelsTemp = new HashMap<String, String>();
		// text
		labelsTemp.put("header_sign_in", "Вход в систему");
		labelsTemp.put("header_sign_up", "Регистрация в системе");
		labelsTemp.put("button_toggle_sign_in", "Вход");
		labelsTemp.put("button_toggle_sign_up", "Регистрация");
		labelsTemp.put("button_submit_sign_in", "Войти");
		labelsTemp.put("button_submit_sign_up", "Зарегистрироваться");
		labelsTemp.put("button_forgot_password", "Забыли пароль?");
		labelsTemp.put("button_close", "Закрыть");
		labelsTemp.put("button_sign_out", "Выйти");
		labelsTemp.put("input_username", "Имя пользователя");
		labelsTemp.put("input_password", "Пароль");
		labelsTemp.put("input_password_repeat", "Подтверждение пароля");
		labelsTemp.put("title_help", "Помощь");
		labelsTemp.put("title_password_restore", "Восстановление пароля");
		// text areas
		labelsTemp.put("textarea_login_help", "Введите имя пользователя и пароль.");
		labelsTemp.put("textarea_login_password_restore", "Для восстановления пароля обратитесь к администратору.");
		labelsTemp.put("textarea_main_start_message", "Вы находитесь в режиме администрирования системы");
		// settings
		labelsTemp.put("settings_uriFaces", "URI модуля 'Люди'");
		labelsTemp.put("settings_uriProjects", "URI модуля 'Проекты'");
		labelsTemp.put("settings_uriTrainings", "URI модуля 'Тренинги'");
		labelsTemp.put("settings_resourcesPath", "Путь к загружаемым ресурсам");
		// errors
		labelsTemp.put("error_empty_fields", "Не все поля заполнены");
		labelsTemp.put("error_upsert", "При внесении изменений произошла ошибка");
		labelsTemp.put("error_username_or_password", "Неверное имя пользователя или пароль");
		labelsTemp.put("error_username_exists", "Пользователь с таким именем уже существует");
		labelsTemp.put("error_passwords_dont_match", "Пароли не совпадают");
		LABELS = Collections.unmodifiableMap(labelsTemp);
	}

	public static String getTextLabel(String ident) {
		String result = LABELS.get(ident);
		return result != null ? result : ident;
	}
}
