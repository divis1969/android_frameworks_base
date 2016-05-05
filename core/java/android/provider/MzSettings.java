//
// Reworked decompiled by Procyon v0.5.30
//

package android.provider;

import java.util.HashSet;

public final class MzSettings
{
    public static final class Global
    {
        public static final String AUTO_ANSWER_INCOMING_RINGING = "auto_answer_incoming_ringing";
        public static final String AUTO_RECORD_ALERT_COUNT = "auto_record_alert_count";
        public static final String AUTO_RECORD_WHEN_CALLING = "auto_record_when_calling";
        public static final String CHARGED_SOUND = "charged_sound";
        public static final String MEIZU_ALI_ENABLE_TRANSFER = "ali_enable_transfer";
        public static final String MEIZU_BLUETOOTH_CONNECT_AFTER_PAIR = "bluetooth_connect_after_pair";
        public static final String MEIZU_BLUETOOTH_HOST_START_PAIR = "bluetooth_host_start_pair";
        public static final String MEIZU_INFO_PHONE_NUMBER_ENABLE = "enable_query_info_phone_number";
        public static final String MEIZU_LAKALA_ENABLE_TRANSFER = "lakala_enable_transfer";
        public static final String MEIZU_NFC_SLEEP_MODE_WAKEUP_SCREEN = "nfc_sleep_mode_wakeup_screen";
        public static final String MEIZU_SHOW_HIDDEN_MENU = "show_hidden_menu";
        public static final String MEIZU_SHOW_NFC_STATUSBAR_SWITCH = "show_nfc_statusbar_switch";
        public static final String MEIZU_SHOW_VPN_STATUSBAR_SWITCH = "show_vpn_statusbar_switch";
        public static final String MEIZU_TETHER_ENABLE_BLUETOOTH = "tether_enable_bluetooth";
        public static final String MZ_WIFI_SCAN_ALWAYS_AVAILABLE_REMEMBERED = "wifi_scan_always_enabled_remembered";
        public static final String PLUDIN_SOUND = "plugin_sound";
        public static final String PREFIX_DIALING_NUMBER = "prefix_dialing_number";
        public static final String VIBRATE_WHEN_MOCALL_CONNECTED = "vibrate_when_mocall_connected";
        public static final String WHITE_LIST_DISTURB_ENABLE = "white_list_disturb_enable";
    }

    public static final class Secure
    {
        public static final String ESE_SMARTMX = "smartmx_ese";
        public static final String MEIZU_DEVICE_NAME = "meizu_device_name";
        public static final String MEIZU_KEYGUARD_LOCK = "meizu_keyguard_lock";
        public static final HashSet<String> MEIZU_NO_SECURE = new HashSet<String>(30);
        public static final String MEIZU_PASSWORD_FRONT_FOUR = "meizu_password_fronts_four";
        public static final String MEIZU_PASSWORD_LENGTH = "meizu_password_length";
        public static final String MEIZU_PASSWORD_TYPE = "meizu_password_type";
        public static final String MZ_BT_SESSION_STATUS = "mz_bt_session_status";
        public static final String MZ_CURRENT_POWER_MODE = "mz_current_power_mode";
        public static final String MZ_DRIVE_MODE = "mz_drive_mode";
        public static final String MZ_DRIVE_MODE_BLUETOOTH_DEVICE = "mz_drive_mode_bluetooth_device";
        public static final String MZ_DRIVE_MODE_BLUETOOTH_DEVICE_NAME = "mz_driver_mode_bluetooth_device_name";
        public static final String MZ_DRIVE_MODE_BLUETOOTH_TRIGGER = "mz_drive_mode_bluetooth_trigger";
        public static final String MZ_DRIVE_MODE_INCALL_VOICE = "mz_drive_mode_incall_voice";
        public static final String MZ_DRIVE_MODE_INMSG_VOICE = "mz_drive_mode_inmsg_voice";
        public static final String MZ_DRIVE_MODE_MUSIC_AUTOPLAY = "mz_drive_mode_music_autoplay";
        public static final String MZ_DRIVE_MODE_REJECT_INCALL_MSG = "mz_drive_mode_reject_incall_msg";
        public static final String MZ_DRIVE_MODE_REJECT_INCALL_MSG_CONTENT = "mz_drive_mode_reject_incall_msg_content";
        public static final String MZ_ENABLE_TETHER_TOTAL = "mz_enable_tether_total";
        public static final String MZ_FINGERPRINT_INDEX_LIST = "mz_fingerprint_index_list";
        public static final String MZ_FINGERPRINT_LAST_ENROLLED_INDEX = "mz_fingerprint_last_enrolled_index";
        public static final String MZ_FINGERPRINT_NAME_PREFIX = "mz_fingerprint_name_";
        public static final String MZ_GUEST_MODE_PASSWORD = "mz_guest_mode_password";
        public static final String MZ_NEED_COMPATIBLE_PASSWORD = "mz_need_compatible_password";
        public static final String MZ_NFCP2P_DRAG_GUIDE = "mz_nfcp2p_drag_guide";
        public static final String MZ_NFCP2P_ON = "mz_nfcp2p_on";
        public static final String MZ_PASSWORD_LENGTH_APPLOCK = "mz_password_length_applock";
        public static final String MZ_PASSWORD_LENGTH_DOCUMENT = "mz_password_length_document";
        public static final String MZ_PASSWORD_TYPE_APPLOCK = "mz_password_type_app_lock";
        public static final String MZ_PASSWORD_TYPE_DOCUMENT = "mz_password_type_document";
        public static final String MZ_SHUTDOWN_VERIFY_PASSWORD = "meizu_shutdown_verify_password";

        static {
            MEIZU_NO_SECURE.add("mz_smartbar_hit_edge_count_inside");
            MEIZU_NO_SECURE.add("mz_smartbar_hit_edge_count_outside");
        }
    }

    public static final class System
    {
        public static final String ALARM_SOUND_FILE_PATH = "alarm_sound_file_path";
        public static final String BUTTON_BRIGHTNESS = "button_brightness";
        public static final String CALENDAR_SOUND = "calendar_sound";
        public static final String CALENDAR_SOUND_FILE_PATH = "calendar_sound_file_path";
        public static final String CAMERA_SOUND = "camera_sound";
        public static final String CAMERA_SOUNDS_ENABLED = "camera_sounds_enabled";
        public static final String CHOOSE_NETWORK_PROVIDER_MODE = "choose_network_provider_mode";
        public static final String CPU_L = "cpu_l";
        public static final String EMAIL_SOUND = "email_sound";
        public static final String EMAIL_SOUND_FILE_PATH = "email_sound_file_path";
        public static final String ENABLE_SIP_CALL = "enable_sip_call";
        public static final String ENABLE_SIP_FEATURE = "enable_sip_feature";
        public static final String FLYME_LOGOUT_CLEAR_FLAG = "flyme_logout_clear_flag";
        public static final String HAPTIC_FEEDBACK_THEME = "haptic_feedback_theme";
        public static final String HIFI_MUSIC = "hifi_music";
        public static final String HIFI_MUSIC_ENABLED = "hifi_music_enabled";
        public static final String HIFI_MUSIC_PARAM = "hifi_music_param";
        public static final String INTERCEPT_POERKEY_BY_APPLICATION = "intercpt_powerkey";
        public static final String INTERNET_CALL_SIP_ENABLE = "internet_call_switch";
        public static final String KEY_SOUND = "key_sound";
        public static final String KEY_SOUNDS_ENABLED = "key_sounds_enabled";
        public static final String KEY_SYSTEM_TIPS_SOUND = "key_system_tips_sound";
        public static final String LIGHT_FEEDBACK_ENABLED = "light_feedback_enabled";
        public static final String LIST_HOLD_SOUND = "list_hold_sound";
        public static final String MEIZU_KEY_WAKEUP_TYPE = "meizu_key_wakeup_type";
        public static final String MEIZU_SHOPDEMO_TOOL_MUSIC = "meizu_shopdemo_tool_music";
        public static final String MEIZU_SHOPDEMO_TOOL_MUSIC_VOLUME = "meizu_shopdemo_tool_music_volume";
        public static final String MEIZU_SHOPDEMO_TOOL_PASSWORD = "meizu_shopdemo_tool_password";
        public static final String MEIZU_SHOPDEMO_TOOL_PLAYTIME = "meizu_shopdemo_tool_playtime";
        public static final String MEIZU_SHOPDEMO_TOOL_PLAYTIME_END = "meizu_shopdemo_tool_playtime_end";
        public static final String MEIZU_SHOPDEMO_TOOL_PLAYTIME_END_DEFAULT_VALUE = "2000";
        public static final String MEIZU_SHOPDEMO_TOOL_PLAYTIME_START = "meizu_shopdemo_tool_playtime_start";
        public static final String MEIZU_SHOPDEMO_TOOL_PLAYTIME_START_DEFAULT_VALUE = "1000";
        public static final String MEIZU_WEEK_START = "week_start";
        public static final String MMS_SOUND = "mms_sound";
        public static final String MMS_SOUND_FILE_PATH = "mms_sound_file_path";
        public static final String MODE_RING_UP = "mode_ring_up";
        public static final String MZ_APP_LOCK_CONTROL = "mz_app_lock_control";
        public static final String MZ_CURRENT_NETWROK_SPEED = "mz_current_network_speed";
        public static final String MZ_DATA_COLLECTION = "meizu_data_collection";
        public static final String MZ_DATA_SERVICE_RUNNING = "mz_data_service_running";
        public static final String MZ_DOCUMENT_LOCK = "mz_document_lock";
        public static final String MZ_DO_NOT_DISTURB_ALLOW_EXIT_DIALOG_FLAG = "mz_do_not_disturb_allow_exit_dialog_flag";
        public static final String MZ_DO_NOT_DISTURB_END_TIME = "mz_do_not_disturb_end_time";
        public static final String MZ_DO_NOT_DISTURB_END_TIME_HOUR = "mz_do_not_disturb_end_time_hour";
        public static final String MZ_DO_NOT_DISTURB_END_TIME_MINUTE = "mz_do_not_disturb_end_time_minutes";
        public static final String MZ_DO_NOT_DISTURB_IS_WORKING = "mz_do_not_disturb_is_working";
        public static final String MZ_DO_NOT_DISTURB_PRE_PULSE_STATE = "mz_do_not_disturb_pre_pulse_state";
        public static final String MZ_DO_NOT_DISTURB_PRE_RINGER_MODE = "mz_do_not_disturb_pre_ringer_mode";
        public static final String MZ_DO_NOT_DISTURB_PRE_VIBRATE_STATE = "mz_do_not_disturb_pre_vibrate_state";
        public static final String MZ_DO_NOT_DISTURB_REMIND_REPEATING_COMING_CALL = "mz_do_not_disturb_remind_repeating_coming _call";
        public static final String MZ_DO_NOT_DISTURB_REPEAT_DAYS = "mz_do_not_disturb_repeat_days";
        public static final String MZ_DO_NOT_DISTURB_SCREEN_NOT_WAKEUP_BY_NOTIFICATION = "mz_do_not_disturb_screen_not_wakeup_by_notification";
        public static final String MZ_DO_NOT_DISTURB_SCREEN_OFF_TIME = "mz_do_not_disturb_screen_off_time";
        public static final String MZ_DO_NOT_DISTURB_START_TIME = "mz_do_not_disturb_start_time";
        public static final String MZ_DO_NOT_DISTURB_START_TIME_HOUR = "mz_do_not_disturb_start_time_hour";
        public static final String MZ_DO_NOT_DISTURB_START_TIME_MINUTE = "mz_do_not_disturb_start_time_minute";
        public static final String MZ_DO_NOT_DISTURB_TIME_SWITCH = "mz_do_not_disturb_time_switch";
        public static final String MZ_DO_NOT_DISTURB_WAKEUP_BREATH_LIGHT = "mz_do_not_disturb_wakeup_breath_light";
        public static final String MZ_EASY_MODE = "mz_easy_mode";
        public static final String MZ_ENABLE_ALARM_ALIGN = "mz_enable_alarm_align";
        public static final String MZ_ENABLE_FULL_SCREEN_DRAG = "enable_full_screen_drag";
        public static final String MZ_FLOAT_TOUCH_ENABLE = "mz_float_touch_enable";
        public static final String MZ_FP_USE_PAYMENT = "mz_fingerprint_use_payment";
        public static final String MZ_FP_USE_UNLOCK = "mz_fingerprint_use_unlock";
        public static final String MZ_GUARD_MODE = "mz_guard_mode";
        public static final String MZ_HOME_KEY_TOUCH_BEHAVIOR = "mz_home_key_touch_behavior";
        public static final String MZ_INTELLIGENT_VOICE = "mz_intelligent_voice";
        public static final String MZ_INTELLIGENT_VOICE_HEADSET_WAKEUP = "mz_intelligent_voice_headset_wakeup";
        public static final String MZ_INTELLIGENT_VOICE_HOME_WAKEUP = "mz_intelligent_voice_home_wakeup";
        public static final String MZ_KEYGUARD_PALM_REJECTION_ENABLED = "keyguard_palm_rejection";
        public static final String MZ_MTP_UNLOCKED = "mz_mtp_unlocked";
        public static final String MZ_POWER_BRIGHT_ALGOL = "mz_power_bright_algol";
        public static final String MZ_POWER_MODE = "mz_power_mode";
        public static final String MZ_QUICK_WAKEUP_DOUBLE_CLICK = "mz_quick_wakeup_double_click";
        public static final String MZ_QUICK_WAKEUP_DRAW_C = "mz_quick_wakeup_draw_c";
        public static final String MZ_QUICK_WAKEUP_DRAW_C_PACKAGE_DETAIL = "mz_quick_wakeup_draw_c_package_detail";
        public static final String MZ_QUICK_WAKEUP_DRAW_E = "mz_quick_wakeup_draw_e";
        public static final String MZ_QUICK_WAKEUP_DRAW_E_PACKAGE_DETAIL = "mz_quick_wakeup_draw_e_package_detail";
        public static final String MZ_QUICK_WAKEUP_DRAW_M = "mz_quick_wakeup_draw_m";
        public static final String MZ_QUICK_WAKEUP_DRAW_M_PACKAGE_DETAIL = "mz_quick_wakeup_draw_m_package_detail";
        public static final String MZ_QUICK_WAKEUP_DRAW_O = "mz_quick_wakeup_draw_o";
        public static final String MZ_QUICK_WAKEUP_DRAW_O_PACKAGE_DETAIL = "mz_quick_wakeup_draw_o_package_detail";
        public static final String MZ_QUICK_WAKEUP_DRAW_S = "mz_quick_wakeup_draw_s";
        public static final String MZ_QUICK_WAKEUP_DRAW_S_PACKAGE_DETAIL = "mz_quick_wakeup_draw_s_package_detail";
        public static final String MZ_QUICK_WAKEUP_DRAW_V = "mz_quick_wakeup_draw_v";
        public static final String MZ_QUICK_WAKEUP_DRAW_V_PACKAGE_DETAIL = "mz_quick_wakeup_draw_v_package_detail";
        public static final String MZ_QUICK_WAKEUP_DRAW_W = "mz_quick_wakeup_draw_w";
        public static final String MZ_QUICK_WAKEUP_DRAW_W_PACKAGE_DETAIL = "mz_quick_wakeup_draw_w_package_detail";
        public static final String MZ_QUICK_WAKEUP_DRAW_Z = "mz_quick_wakeup_draw_z";
        public static final String MZ_QUICK_WAKEUP_DRAW_Z_PACKAGE_DETAIL = "mz_quick_wakeup_draw_z_package_detail";
        public static final String MZ_QUICK_WAKEUP_SLIDE_DOWN = "mz_quick_wakeup_slide_down";
        public static final String MZ_QUICK_WAKEUP_SLIDE_LEFT = "mz_quick_wakeup_slide_left";
        public static final String MZ_QUICK_WAKEUP_SLIDE_LEFT_RIGHT = "mz_quick_wakeup_slide_left_right";
        public static final String MZ_QUICK_WAKEUP_SLIDE_RIGHT = "mz_quick_wakeup_slide_right";
        public static final String MZ_QUICK_WAKEUP_SLIDE_RIGHT_PACKAGE_DETAIL = "mz_quick_wakeup_slide_right_package_detail";
        public static final String MZ_QUICK_WAKEUP_SLIDE_UP = "mz_quick_wakeup_slide_up";
        public static final String MZ_QUICK_WAKEUP_SWITCH = "mz_quick_wakeup_switch";
        public static final String MZ_SCHEDULED_POWER_OFF = "mz_scheduled_power_off";
        public static final String MZ_SCHEDULED_POWER_OFF_DAYS = "mz_scheduled_power_off_days";
        public static final String MZ_SCHEDULED_POWER_OFF_H = "mz_scheduled_power_off_h";
        public static final String MZ_SCHEDULED_POWER_OFF_M = "mz_scheduled_power_off_m";
        public static final String MZ_SCHEDULED_POWER_OFF_TIME = "mz_scheduled_power_off_time";
        public static final String MZ_SCHEDULED_POWER_ON = "mz_scheduled_power_on";
        public static final String MZ_SCHEDULED_POWER_ON_DAYS = "mz_scheduled_power_on_days";
        public static final String MZ_SCHEDULED_POWER_ON_H = "mz_scheduled_power_on_h";
        public static final String MZ_SCHEDULED_POWER_ON_M = "mz_scheduled_power_on_m";
        public static final String MZ_SCHEDULED_POWER_ON_TIME = "mz_scheduled_power_on_time";
        public static final String MZ_SCREEN_ON_WHILE_NOTIFICATION = "mz_screen_on_while_notification";
        public static final String MZ_SCREEN_SAVE_MODE = "mz_screen_save_mode";
        public static final String MZ_SET_WALLPAPER_SIMULTANEOUSLY = "set_wallpaper_simultaneously";
        public static final String MZ_SHOW_ACCESS_PASSWORD = "mz_show_access_password";
        public static final String MZ_SHOW_MTP_NOTIFICATION = "mz_show_mtp_notification";
        public static final String MZ_SHOW_USER_GUIDE = "mz_show_user_guide";
        public static final String MZ_SMARTBAR_AUTO_HIDE = "mz_smartbar_auto_hide";
        public static final String MZ_SMARTBAR_HEIGHT_VALUE = "mz_smartbar_height_value";
        public static final String MZ_SMARTBAR_HIT_EDGE_COUNT_INSIDE = "mz_smartbar_hit_edge_count_inside";
        public static final String MZ_SMARTBAR_HIT_EDGE_COUNT_OUTSIDE = "mz_smartbar_hit_edge_count_outside";
        public static final String MZ_SMARTBAR_PADDING = "mz_smartbar_padding";
        public static final String MZ_SMARTBAR_PADDING_VALUE = "mz_smartbar_padding_value";
        public static final String MZ_SMART_TOUCH_BACK_DISABLE = "mz_float_touch_enable";
        public static final String MZ_SMART_TOUCH_BEHAVIOR_ALPHA = "mz_smart_touch_behavior_alpha";
        public static final String MZ_SMART_TOUCH_BEHAVIOR_CLICK = "mz_smart_touch_behavior_click";
        public static final String MZ_SMART_TOUCH_BEHAVIOR_DOUBLECLICK = "mz_smart_touch_behavior_doubleclick";
        public static final String MZ_SMART_TOUCH_BEHAVIOR_DOWN = "mz_smart_touch_behavior_down";
        public static final String MZ_SMART_TOUCH_BEHAVIOR_LEFTRIGHT = "mz_smart_touch_behavior_leftright";
        public static final String MZ_SMART_TOUCH_BEHAVIOR_LONG = "mz_smart_touch_behavior_long";
        public static final String MZ_SMART_TOUCH_BEHAVIOR_UP = "mz_smart_touch_behavior_up";
        public static final String MZ_SMART_TOUCH_SWITCH = "mz_smart_touch_switch";
        public static final String MZ_SMART_VOICE_WAKEUP_BY_VOICE = "mz_smart_voice_wakeup_by_voice";
        public static final String MZ_STATUS_BAR_TINT = "mz_status_bar_tint";
        public static final String MZ_USE_FLYME_COMMUNICATION = "mz_use_flyme_communication";
        public static final String NETWORK_PROVIDER_PACKAGE = "network_provider_package";
        public static final String NOTIFICATION_SOUND_FILE_PATH = "notification_sound_file_path";
        public static final String REJECT_WHEN_OUTOF_PHONEBOOK = "reject_when_outof_phonebook";
        public static final String REJECT_WHEN_UNKOWN_UNMBER = "reject_when_unkown_number";
        public static final String RINGTONE_SOUND_FILE_PATH = "ringtone_sound_file_path";
        public static final String SCREEN_BRIGHTNESS_ANIMATION = "animation_brightness";
        public static final String SCREEN_BRIGHTNESS_PREFERED = "prefer_screen_brightness";
        public static final String SCREEN_LUX_PREFERED = "prefer_screen_lux";
        public static final String SNS_ENALBE = "sns_enable";
        public static final String SOUND_SPDIF = "sound_spdif";
        public static final String SOUND_SPDIF_BUTTON = "sound_spdif_button";
        public static final String SPAM_CALL_FILTER_ENABLE = "spam_call_filter_enable";
        public static final String SPAM_CALL_FILTER_FROM_CLOUD_LEVEL = "spam_call_filter_from_cloud_level";
        public static final String STATUSBAR_BATTERY_PERCENT = "statusbar_battery_percent";
    }
}
