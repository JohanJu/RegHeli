/* Produced by CVXGEN, 2016-05-10 05:35:37 -0400.  */
/* CVXGEN is Copyright (C) 2006-2012 Jacob Mattingley, jem@cvxgen.com. */
/* The code in this file is Copyright (C) 2006-2012 Jacob Mattingley. */
/* CVXGEN, or solvers produced by CVXGEN, cannot be used for commercial */
/* applications without prior written permission from Jacob Mattingley. */

/* Filename: testsolver.c. */
/* Description: Basic test harness for solver.c. */
#include "solver.h"
Vars vars;
Params params;
Workspace work;
Settings settings;
void print()
{
    printf("\nx\n\n");
    for (int i = 0; i < 6; ++i)
    {
        printf("%f %f %f %f\n", *vars.x_1,*vars.x_4,*vars.x_7,*vars.x_10);
        vars.x_1++;
        vars.x_4++;
        vars.x_7++;
        vars.x_10++;
    }
    printf("\nu\n\n");
    for (int i = 0; i < 2; ++i)
    {
        printf("%f %f %f %f\n", *vars.u_0, *vars.u_4, *vars.u_7, *vars.u_10);
        vars.u_0++;
        vars.u_4++;
        vars.u_7++;
        vars.u_10++;
    }
}


#define NUMTESTS 1000
int main(int argc, char **argv)
{
    int num_iters;
#if (NUMTESTS > 0)
    int i;
    double time;
    double time_per;
#endif
    set_defaults();
    setup_indexing();
    load_default_data();
    /* Solve problem instance for the record. */
    settings.verbose = 0;
    num_iters = solve();
#ifndef ZERO_LIBRARY_MODE
#if (NUMTESTS > 0)
    /* Now solve multiple problem instances for timing purposes. */
    settings.verbose = 0;
    tic();
    for (i = 0; i < NUMTESTS; i++)
    {

        for (int i = 0; i < 2; ++i)
        {
            *vars.u_0 = 100;
            vars.u_0++;
        }

        solve();
        // print();
    }
    time = tocq();
    printf("Timed %d solves over %.3f seconds.\n", NUMTESTS, time);
    time_per = time / NUMTESTS;
    if (time_per > 1)
    {
        printf("Actual time taken per solve: %.3g s.\n", time_per);
    }
    else if (time_per > 1e-3)
    {
        printf("Actual time taken per solve: %.3g ms.\n", 1e3 * time_per);
    }
    else
    {
        printf("Actual time taken per solve: %.3g us.\n", 1e6 * time_per);
    }
#endif
#endif
    return 0;
}
void load_default_data(void)
{
    params.x_0[0] = 0;
    params.x_0[1] = 0;
    params.x_0[2] = 0;
    params.x_0[3] = 10;
    params.x_0[4] = 0;
    params.x_0[5] = 0;
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
}