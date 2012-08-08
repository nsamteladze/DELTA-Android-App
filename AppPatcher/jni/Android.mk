
LOCAL_PATH:= $(call my-dir)

# first lib, which will be built statically
#
include $(CLEAR_VARS)

bzlib_files := \
	blocksort.c \
	huffman.c \
	crctable.c \
	randtable.c \
	compress.c \
	decompress.c \
	bzlib.c

LOCAL_MODULE := libbz
LOCAL_SRC_FILES := $(bzlib_files)

include $(BUILD_STATIC_LIBRARY)

# second lib, which will depend on and include the first one
#
include $(CLEAR_VARS)
LOCAL_MODULE    := bspatch
LOCAL_SRC_FILES := bspatch.c

LOCAL_STATIC_LIBRARIES := libbz
include $(BUILD_STATIC_LIBRARY)

# third lib, which will depend on and include the scond one
#
include $(CLEAR_VARS)

LOCAL_MODULE    := native
LOCAL_SRC_FILES := native.c

LOCAL_STATIC_LIBRARIES := bspatch
# Include log library
LOCAL_LDLIBS := -L$(SYSROOT)/usr/lib -llog 
include $(BUILD_SHARED_LIBRARY)