
LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := $(call all-subdir-java-files)

LOCAL_JAVA_LIBRARIES :=

LOCAL_JNI_SHARED_LIBRARIES := libandroid_runtime

LOCAL_PACKAGE_NAME := UpdateUdisk 

LOCAL_CERTIFICATE := platform

LOCAL_PROGUARD_ENABLED := disabled
LOCAL_DEX_PREOPT := false

LOCAL_AAPT_FLAGS += -c ldpi -c mdpi -c hdpi 

ALL_DEFAULT_INSTALLED_MODULES += $(LOCAL_PACKAGE_NAME)
include $(BUILD_PACKAGE)

