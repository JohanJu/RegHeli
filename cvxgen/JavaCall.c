#include <jni.h>
#include "JavaCall.h"
#include "solver.h"

Vars vars;
Params params;
Workspace work;
Settings settings;

void load_default_data(void)
{
    /* Make this a diagonal PSD matrix, even though it's not diagonal. */

    FILE *pFile;
    double d;
    pFile = fopen ("q.txt", "r");
    for (int i = 0; i < 36; ++i)
    {
        fscanf (pFile, "%lf", &d);
        params.Q[i] = d;
    }
    fclose (pFile);
    pFile = fopen ("r.txt", "r");
    for (int i = 0; i < 4; ++i)
    {
        fscanf (pFile, "%lf", &d);
        params.R[i] = d;
    }
    fclose (pFile);
    pFile = fopen ("a.txt", "r");
    for (int i = 0; i < 36; ++i)
    {
        fscanf (pFile, "%lf", &d);
        params.A[i] = d;
    }
    fclose (pFile);
    pFile = fopen ("b.txt", "r");
    for (int i = 0; i < 12; ++i)
    {
        fscanf (pFile, "%lf", &d);
        params.B[i] = d;
    }
    fclose (pFile);


    params.u_max[0] = 7.3079;
    params.u_min[0] = -12.6921;
    params.u_max[1] = 13.8399;
    params.u_min[1] = -6.1601;
    params.S[0] = 100000;
    settings.verbose = 0;
}

JNIEXPORT void JNICALL Java_JavaCall_load
  (JNIEnv *env, jobject thisObj){
    set_defaults();
    setup_indexing();
    load_default_data();

  }

JNIEXPORT jdoubleArray JNICALL Java_JavaCall_mpc
(JNIEnv *env, jobject thisObj, jdoubleArray inJNIArray)
{
    // Step 1: Convert the incoming JNI jintarray to C's jint[]
    jdouble *inCArray = (*env)->GetDoubleArrayElements(env, inJNIArray, NULL);
    if (NULL == inCArray) return NULL;
    jsize length = (*env)->GetArrayLength(env, inJNIArray);
    int i;
    for (i = 0; i < length; i++)
    {
        params.x_0[i]  = inCArray[i];
    }
    
    solve();
    (*env)->ReleaseDoubleArrayElements(env, inJNIArray, inCArray, 0); // release resources

    
    jdouble outCArray[] = {vars.u_0[0], vars.u_0[1]};

    // Step 3: Convert the C's Native jdouble[] to JNI jdoublearray, and return
    jdoubleArray outJNIArray = (*env)->NewDoubleArray(env, 2);  // allocate
    if (NULL == outJNIArray) return NULL;
    (*env)->SetDoubleArrayRegion(env, outJNIArray, 0 , 2, outCArray);  // copy
    return outJNIArray;
}