package com.example.user.connect4;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class single_player_activity extends AppCompatActivity {

    public Boolean player = true;
    public char playerwon;
    int[] top = new int[8];
    ImageView[][] img = new ImageView[7][8];
    ImageView[] win = new ImageView[5];
    Random rand = new Random();
    int cell_ck, update_ck_ai;
    int count_ai_row_update, count_ai_col_update;
    int[] cnt_ai_arr = new int[8];
    int[] cnt_ai_row_arr = new int[8];
    int[] cnt_ai_col_arr = new int[8];
    int update_ck_hm;
    int count_hm_row_update, count_hm_col_update;
    int[] cnt_hm_arr = new int[8];
    int[] cnt_hm_row_arr = new int[8];
    int[] cnt_hm_col_arr = new int[8];
    int[] aibestmove_arr = new int[500];
    int[] humanbestmove_arr = new int[500];
    int[] aibestclm = new int[500];
    int[] aibestrow = new int[500];
    int[] humanbestclm = new int[500];
    int[] humanbestrow = new int[500];
    int storebest_ai, storebest_human, jj, kk;
    int[] store_ai = new int[100];
    int[] store_human = new int[100];
    int[] store_ai_row = new int[100];
    int[] store_ai_clm = new int[100];
    int[] store_human_row = new int[100];
    int[] store_human_clm = new int[100];
    int updateplace_ck = 0;
    private char[][] place = new char[7][8];

    int dfs_col_depth(int count_row, int count_col) {

        if ((count_row - 1) < 1) {
            return 1;
        } else if (place[count_row - 1][count_col] == '_') {
            return 0;
        } else return 1;
    }

    // AI Turn here AI Turn here AI Turn here AI Turn here AI Turn here AI Turn here AI Turn here AI Turn here
    void aimove() {

        // Log.i( "aimove check  aiaiai","aimove method aimove method aimove method!!!");

        int cntai, cnt_ai_stor = 0, cnt_ai_hori = 0, i, j, x, z, row = 0, col = 0;
        int count_ai_row, count_ai_col;

        updateplace_ck = 0;

        /** memory set by 0 */
        for (int ii = 0; ii < 500; ii++) {
            aibestmove_arr[ii] = 0;
            humanbestmove_arr[ii] = 0;
            aibestclm[ii] = 0;
            aibestrow[ii] = 0;
            humanbestclm[ii] = 0;
            humanbestrow[ii] = 0;
        }
        jj = 1;
        kk = 1;

        //checking Horizontal row for AI --------------------------------------------------------------------

        update_ck_ai = 0;
        update_ck_hm = 0;
        int twotimes;
        char ch = 'c';

        for (int w = 1; w <= 2; w++) { // w=1 for AI board checking and w=2 for human

            cnt_ai_hori = 0;
            for (i = 1; i <= 6; i++) {
                cntai = 1;
                x = 0;
                z = 0;
                /** memset by zero*/
                for (int xx = 0; xx < 8; xx++) {
                    cnt_ai_arr[xx] = 0;
                    cnt_ai_row_arr[xx] = 0;
                    cnt_ai_col_arr[xx] = 0;
                }

                row = 0;
                col = 0;
                // if three coin matching in horizontal row
                for (int s = 1; s <= 7; s++) {
                    cnt_ai_hori = 0;
                    if (place[i][s] == ch) {
                        while (s <= 7 && place[i][s] != '_' && place[i][s] == ch) {
                            cnt_ai_hori++;
                            s++;
                        }
                        if (s <= 7 && place[i][s] == '_') {
                            row = i;
                            col = s;
                            ++s;
                            if (s <= 7 && place[i][s] != '_' && place[i][s] == ch) {
                                while (s <= 7 && place[i][s] != '_' && place[i][s] == ch) {
                                    cnt_ai_hori++;
                                    s++;
                                }
                                if (cnt_ai_hori >= 3) {
                                    int position = dfs_col_depth(row, col);
                                    if (position == 1) {//check move is valid or invalid

                                        if (top[col] < 6) {
                                            ++top[col];

                                            place[row][col] = 'c';
                                            count_ai_row_update = 0;
                                            count_ai_col_update = 0;
                                            count_hm_row_update = 0;
                                            count_hm_col_update = 0;
                                            updateplace_ck = 1;
                                            cnt_ai_hori = 0;

                                            updateplace();
                                            return;
                                        }
                                    }
                                } else {
                                    --s;
                                    cnt_ai_hori = 0;
                                }
                            } else {
                                --s;
                                cnt_ai_hori = 0;
                            }
                        } else {
                            --s;
                            cnt_ai_hori = 0;
                        }
                    }
                    row = 0;
                    col = 0;
                }
                // if there is not matching three coin
                for (j = 2; j <= 7; j++) {
                    if (place[i][j - 1] == ch) {
                        if (place[i][j - 1] == place[i][j]) {
                            if (cntai == 1)
                                win[cntai] = img[i][j - 1];
                            cntai++;
                            win[cntai] = img[i][j];
                            if (j == 7) {
                                cnt_ai_stor = cntai;
                                count_ai_row = i;
                                count_ai_col = j;
                                if (count_ai_col - (cnt_ai_stor) >= 1 && place[count_ai_row][count_ai_col - (cnt_ai_stor)] == '_') {
                                    //cell_ck = 1;
                                    int position = dfs_col_depth(count_ai_row, (count_ai_col - cnt_ai_stor));
                                    if (position == 1) {
                                        cnt_ai_arr[z++] = cntai;
                                        cnt_ai_row_arr[x] = i;
                                        cnt_ai_col_arr[x] = (count_ai_col - cnt_ai_stor);
                                        x++;
                                    }
                                }
                            }
                        } else {
                            if (cntai <= 7) {
                                cnt_ai_stor = cntai;
                                count_ai_row = i;
                                count_ai_col = j;
                                if (j <= 7 && place[count_ai_row][count_ai_col] == '_') {

                                    //cell_ck = 1;
                                    int position = dfs_col_depth(count_ai_row, count_ai_col);
                                    if (position == 1) {
                                        cnt_ai_arr[z++] = cntai;
                                        cnt_ai_row_arr[x] = i;
                                        cnt_ai_col_arr[x] = j;
                                        position = 0;
                                        x++;
                                    }
                                }
                                if (count_ai_col - (cnt_ai_stor + 1) >= 1 && place[count_ai_row][count_ai_col - (cnt_ai_stor + 1)] == '_') {
                                    //cell_ck = 1;
                                    int position = dfs_col_depth(count_ai_row, (count_ai_col - (cnt_ai_stor + 1)));
                                    if (position == 1) {
                                        cnt_ai_arr[z++] = cntai;
                                        cnt_ai_row_arr[x] = i;
                                        cnt_ai_col_arr[x] = (count_ai_col - (cnt_ai_stor + 1));
                                        x++;
                                    }
                                }
                            }
                            cntai = 1;
                        }
                    } else {
                        // if 7th colomn is valid or invalid
                        if (j == 7 && place[i][j] == ch) {
                            cnt_ai_stor = cntai;
                            count_ai_row = i;
                            count_ai_col = j;
                            if (count_ai_col - (cnt_ai_stor) >= 1 && place[count_ai_row][count_ai_col - (cnt_ai_stor)] == '_') {
                                //cell_ck = 1;
                                int position = dfs_col_depth(count_ai_row, (count_ai_col - cnt_ai_stor));
                                if (position == 1) {
                                    cnt_ai_arr[z++] = cntai;
                                    cnt_ai_row_arr[x] = i;
                                    cnt_ai_col_arr[x] = (count_ai_col - cnt_ai_stor);
                                    x++;
                                }
                            }
                        }

                        cntai = 1;
                    }
                }

                /// each single row check for the best position
                if (w == 1) { // for AI best position store
                    storebest_ai = 0;
                    for (int r = 0; r < z; r++) {
                        if (r == 0) {
                            storebest_ai = cnt_ai_arr[r];
                        } else {
                            if (storebest_ai < cnt_ai_arr[r]) {
                                storebest_ai = cnt_ai_arr[r];
                            }
                        }
                    }
                    for (int r = 0; r < z; r++) { // same best move store for AI
                        if (storebest_ai != 0 && storebest_ai == cnt_ai_arr[r]) {
                            aibestmove_arr[jj] = cnt_ai_arr[r];
                            aibestrow[jj] = cnt_ai_row_arr[r];
                            aibestclm[jj] = cnt_ai_col_arr[r];
                            jj++;
                        }
                    }


//                    for (int t = 0; t < z; t++) {
//                        if (cnt_ai_arr[t] != 0 && (cnt_ai_row_arr[t] != 0 && cnt_ai_col_arr[t] != 0)) {
//                            if (update_ck_ai <= cnt_ai_arr[t]) {
//                                update_ck_ai = cnt_ai_arr[t];
//                                count_ai_row_update = cnt_ai_row_arr[t];
//                                count_ai_col_update = cnt_ai_col_arr[t];
//                            }
//                        }
//                    }
                } else { // for Human best position store
                    storebest_human = 0;
                    for (int r = 0; r < z; r++) {
                        if (r == 0) {
                            storebest_human = cnt_ai_arr[r];
                        } else {
                            if (storebest_human < cnt_ai_arr[r]) {
                                storebest_human = cnt_ai_arr[r];
                            }
                        }
                    }

                    for (int r = 0; r < z; r++) { // same best move store for human
                        if (storebest_human != 0 && storebest_human == cnt_ai_arr[r]) {
                            humanbestmove_arr[kk] = cnt_ai_arr[r];
                            humanbestrow[kk] = cnt_ai_row_arr[r];
                            humanbestclm[kk] = cnt_ai_col_arr[r];
                            kk++;
                        }
                    }


//                    for (int t = 0; t < z; t++) {
//                        if (cnt_ai_arr[t] != 0 && (cnt_ai_row_arr[t] != 0 && cnt_ai_col_arr[t] != 0)) {
//                            if (update_ck_hm <= cnt_ai_arr[t]) {
//                                update_ck_hm = cnt_ai_arr[t];
//                                count_hm_row_update = cnt_ai_row_arr[t];
//                                count_hm_col_update = cnt_ai_col_arr[t];
//                            }
//                        }
//                    }
                }
            }

            ch = 'p';

        }

        ch = 'c';

//checking Vertical search for AI |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

        for (int w = 1; w <= 2; w++) {
            for (i = 1; i <= 7; i++) {
                cntai = 1;
                x = 0;
                z = 0;
                // memset by zero
                for (int xx = 0; xx < 8; xx++) {
                    cnt_ai_arr[xx] = 0;
                    cnt_ai_row_arr[xx] = 0;
                    cnt_ai_col_arr[xx] = 0;
                }
                for (j = 2; j <= 6; j++) {
                    if (place[j - 1][i] == ch) {
                        if (place[j - 1][i] == place[j][i]) {
                            if (cntai == 1)
                                win[cntai] = img[j - 1][i];
                            cntai++;
                            win[cntai] = img[j][i];
                        } else {
                            if (cntai <= 7) {
                                if (j <= 6 && place[j][i] == '_') {
                                    cnt_ai_arr[z++] = cntai;
                                    cnt_ai_row_arr[x] = j;
                                    cnt_ai_col_arr[x] = i;
                                    x++;
                                }
                            }
                            cntai = 1;
                        }
                    } else {
                        cntai = 1;
                    }
                }

                /// each single colomn check for the best position
                if (w == 1) {
                    storebest_ai = 0;
                    for (int r = 0; r < z; r++) {
                        if (r == 0) {
                            storebest_ai = cnt_ai_arr[r];
                        } else {
                            if (storebest_ai < cnt_ai_arr[r]) {
                                storebest_ai = cnt_ai_arr[r];
                            }
                        }
                    }

                    for (int r = 0; r < z; r++) { // same best move store for AI
                        if (storebest_ai != 0 && storebest_ai == cnt_ai_arr[r]) {
                            aibestmove_arr[jj] = cnt_ai_arr[r];
                            aibestrow[jj] = cnt_ai_row_arr[r];
                            aibestclm[jj] = cnt_ai_col_arr[r];
                            jj++;
                        }
                    }


//             for (int t = 0; t < z; t++) {
//                 if (cnt_ai_arr[t] != 0 && (cnt_ai_row_arr[t] != 0 && cnt_ai_col_arr[t] != 0)) {
//                     if (update_ck_ai <= cnt_ai_arr[t]) {
//                         update_ck_ai = cnt_ai_arr[t];
//                         count_ai_row_update = cnt_ai_row_arr[t];
//                         count_ai_col_update = cnt_ai_col_arr[t];
//                     }
//                 }
//             }
                } else {
                    storebest_human = 0;
                    for (int r = 0; r < z; r++) {
                        if (r == 0) {
                            storebest_human = cnt_ai_arr[r];
                        } else {
                            if (storebest_human < cnt_ai_arr[r]) {
                                storebest_human = cnt_ai_arr[r];
                            }
                        }
                    }

                    for (int r = 0; r < z; r++) { // same best move store for human
                        if (storebest_human != 0 && storebest_human == cnt_ai_arr[r]) {
                            humanbestmove_arr[kk] = cnt_ai_arr[r];
                            humanbestrow[kk] = cnt_ai_row_arr[r];
                            humanbestclm[kk] = cnt_ai_col_arr[r];
                            kk++;
                        }
                    }


//             for (int t = 0; t < z; t++) {
//                 if (cnt_ai_arr[t] != 0 && (cnt_ai_row_arr[t] != 0 && cnt_ai_col_arr[t] != 0)) {
//                     if (update_ck_hm <= cnt_ai_arr[t]) {
//                         update_ck_hm = cnt_ai_arr[t];
//                         count_hm_row_update = cnt_ai_row_arr[t];
//                         count_hm_col_update = cnt_ai_col_arr[t];
//                     }
//                 }
//             }
                }
            }

            ch = 'p';
        }
//checking Diagonal search ############################################################################################
        /// diagonal_1 and diagonal_2 (from top-left to bottom-right)
        /// diagonal_3 and diagonal_4 (from top-right to bottom-left)

        // diagonal_1 check (from top-left to bottom-right)
        int m;

        ch = 'c';

        for (int w = 1; w <= 2; w++) {
            cnt_ai_hori = 0;

            for (m = 1; m <= 3; m++) {
                cntai = 1;
                x = 0;
                z = 0;
                // memset by zero
                for (int xx = 0; xx < 8; xx++) {
                    cnt_ai_arr[xx] = 0;
                    cnt_ai_row_arr[xx] = 0;
                    cnt_ai_col_arr[xx] = 0;
                }

                row = 0;
                col = 0;
                for (i = m, j = 1; i <= 6; i++, j++) {
                    cnt_ai_hori = 0;
                    if (place[i][j] == ch) {
                        while (i <= 6 && place[i][j] != '_' && place[i][j] == ch) {
                            cnt_ai_hori++;
                            i++;
                            j++;
                        }
                        if (i <= 6 && place[i][j] == '_') {
                            row = i;
                            col = j;
                            ++i;
                            ++j;
                            if (i <= 6 && place[i][j] != '_' && place[i][j] == ch) {
                                while (j <= 6 && place[i][j] != '_' && place[i][j] == ch) {
                                    cnt_ai_hori++;
                                    i++;
                                    j++;
                                }
                                if (cnt_ai_hori >= 3) {
                                    int position = dfs_col_depth(row, col);
                                    if (position == 1) {
                                        if (top[col] < 6) {
                                            ++top[col];

                                            place[row][col] = 'c';
                                            count_ai_row_update = 0;
                                            count_ai_col_update = 0;
                                            count_hm_row_update = 0;
                                            count_hm_col_update = 0;
                                            updateplace_ck = 1;
                                            cnt_ai_hori = 0;

                                            updateplace();
                                            return;
                                        }
                                    }
                                } else {
                                    --i;
                                    --j;
                                    cnt_ai_hori = 0;
                                }
                            } else {
                                --i;
                                --j;
                                cnt_ai_hori = 0;
                            }
                        } else {
                            --i;
                            --j;
                            cnt_ai_hori = 0;
                        }
                    }
                    row = 0;
                    col = 0;
                }

                for (i = m, j = 1; i <= 5; i++, j++) {
                    if (place[i][j] == ch) {
                        if (place[i][j] == place[i + 1][j + 1]) {
                            if (cntai == 1)
                                win[cntai] = img[i][j];
                            cntai++;
                            win[cntai] = img[i + 1][j + 1];
                        } else {
                            if (cntai <= 6) {
                                cnt_ai_stor = cntai;
                                count_ai_row = i + 1;
                                count_ai_col = j + 1;
                                if (((i + 1) <= 6 && (j + 1) <= 6) && place[count_ai_row][count_ai_col] == '_') {

                                    //cell_ck = 1;
                                    int position = dfs_col_depth(count_ai_row, count_ai_col);
                                    if (position == 1) {
                                        cnt_ai_arr[z++] = cntai;
                                        cnt_ai_row_arr[x] = count_ai_row;
                                        cnt_ai_col_arr[x] = count_ai_col;
                                        x++;
                                    }
                                }
                                if ((((count_ai_row - (cnt_ai_stor + 1)) >= 1) && ((count_ai_col - (cnt_ai_stor + 1)) >= 1)) && place[count_ai_row - (cnt_ai_stor + 1)][count_ai_col - (cnt_ai_stor + 1)] == '_') {
                                    //cell_ck = 1;
                                    int position = dfs_col_depth((count_ai_row - (cnt_ai_stor + 1)), (count_ai_col - (cnt_ai_stor + 1)));
                                    if (position == 1) {
                                        cnt_ai_arr[z++] = cntai;
                                        cnt_ai_row_arr[x] = (count_ai_row - (cnt_ai_stor + 1));
                                        cnt_ai_col_arr[x] = (count_ai_col - (cnt_ai_stor + 1));
                                        x++;
                                    }
                                }
                            }
                            cntai = 1;
                        }
                    } else {
                        cntai = 1;
                    }
                }

                /// each single diagonal_1 check for the best position
                if (w == 1) {
                    storebest_ai = 0;
                    for (int r = 0; r < z; r++) {
                        if (r == 0) {
                            storebest_ai = cnt_ai_arr[r];
                        } else {
                            if (storebest_ai < cnt_ai_arr[r]) {
                                storebest_ai = cnt_ai_arr[r];
                            }
                        }
                    }

                    for (int r = 0; r < z; r++) { // same best move store for AI
                        if (storebest_ai != 0 && storebest_ai == cnt_ai_arr[r]) {
                            aibestmove_arr[jj] = cnt_ai_arr[r];
                            aibestrow[jj] = cnt_ai_row_arr[r];
                            aibestclm[jj] = cnt_ai_col_arr[r];
                            jj++;
                        }
                    }


//                    for (int t = 0; t < z; t++) {
//                        if (cnt_ai_arr[t] != 0 && (cnt_ai_row_arr[t] != 0 && cnt_ai_col_arr[t] != 0)) {
//                            if (update_ck_ai <= cnt_ai_arr[t]) {
//                                update_ck_ai = cnt_ai_arr[t];
//                                count_ai_row_update = cnt_ai_row_arr[t];
//                                count_ai_col_update = cnt_ai_col_arr[t];
//                            }
//                        }
//                    }
                } else {
                    storebest_human = 0;
                    for (int r = 0; r < z; r++) {
                        if (r == 0) {
                            storebest_human = cnt_ai_arr[r];
                        } else {
                            if (storebest_human < cnt_ai_arr[r]) {
                                storebest_human = cnt_ai_arr[r];
                            }
                        }
                    }

                    for (int r = 0; r < z; r++) { // same best move store for human
                        if (storebest_human != 0 && storebest_human == cnt_ai_arr[r]) {
                            humanbestmove_arr[kk] = cnt_ai_arr[r];
                            humanbestrow[kk] = cnt_ai_row_arr[r];
                            humanbestclm[kk] = cnt_ai_col_arr[r];
                            kk++;
                        }
                    }


//                    for (int t = 0; t < z; t++) {
//                        if (cnt_ai_arr[t] != 0 && (cnt_ai_row_arr[t] != 0 && cnt_ai_col_arr[t] != 0)) {
//                            if (update_ck_hm <= cnt_ai_arr[t]) {
//                                update_ck_hm = cnt_ai_arr[t];
//                                count_hm_row_update = cnt_ai_row_arr[t];
//                                count_hm_col_update = cnt_ai_col_arr[t];
//                            }
//                        }
//                    }
                }
            }
            ch = 'p';
        }

        ch = 'c';


// diagonal_2 check (from top-left to bottom-right)

        for (int w = 1; w <= 2; w++) {
            cnt_ai_hori = 0;

            for (m = 2; m <= 4; m++) {
                cntai = 1;
                x = 0;
                z = 0;
                // memset by zero
                for (int xx = 0; xx < 8; xx++) {
                    cnt_ai_arr[xx] = 0;
                    cnt_ai_row_arr[xx] = 0;
                    cnt_ai_col_arr[xx] = 0;
                }

                row = 0;
                col = 0;
                for (i = 1, j = m; j <= 7; i++, j++) { // check, upper position is valid or invalid
                    cnt_ai_hori = 0;
                    if (place[i][j] == ch) {
                        while (j <= 7 && place[i][j] != '_' && place[i][j] == ch) {
                            cnt_ai_hori++;
                            i++;
                            j++;
                        }
                        if (j <= 7 && place[i][j] == '_') {
                            row = i;
                            col = j;
                            ++i;
                            ++j;
                            if (j <= 7 && place[i][j] != '_' && place[i][j] == ch) {
                                while (j <= 7 && place[i][j] != '_' && place[i][j] == ch) {
                                    cnt_ai_hori++;
                                    i++;
                                    j++;
                                }
                                if (cnt_ai_hori >= 3) {
                                    int position = dfs_col_depth(row, col);
                                    if (position == 1) {
                                        if (top[col] < 6) {
                                            ++top[col];

                                            place[row][col] = 'c';
                                            count_ai_row_update = 0;
                                            count_ai_col_update = 0;
                                            count_hm_row_update = 0;
                                            count_hm_col_update = 0;
                                            updateplace_ck = 1;
                                            cnt_ai_hori = 0;

                                            updateplace();
                                            return;
                                        }
                                    }
                                } else {
                                    --i;
                                    --j;
                                    cnt_ai_hori = 0;
                                }
                            } else {
                                --i;
                                --j;
                                cnt_ai_hori = 0;
                            }
                        } else {
                            --i;
                            --j;
                            cnt_ai_hori = 0;
                        }
                    }
                    row = 0;
                    col = 0;
                }

                for (i = 1, j = m; j <= 6; i++, j++) {
                    if (place[i][j] == ch) {
                        if (place[i][j] == place[i + 1][j + 1]) {
                            if (cntai == 1)
                                win[cntai] = img[i][j];
                            cntai++;
                            win[cntai] = img[i + 1][j + 1];
                        } else {
                            if (cntai <= 6) {
                                cnt_ai_stor = cntai;
                                count_ai_row = i + 1;
                                count_ai_col = j + 1;
                                if (((i + 1) <= 6 && (j + 1) <= 7) && place[count_ai_row][count_ai_col] == '_') {

                                    //cell_ck = 1;
                                    int position = dfs_col_depth(count_ai_row, count_ai_col);
                                    if (position == 1) {
                                        cnt_ai_arr[z++] = cntai;
                                        cnt_ai_row_arr[x] = count_ai_row;
                                        cnt_ai_col_arr[x] = count_ai_col;
                                        x++;
                                    }
                                }
                                if ((count_ai_row - (cnt_ai_stor + 1) >= 1) && place[count_ai_row - (cnt_ai_stor + 1)][count_ai_col - (cnt_ai_stor + 1)] == '_') {
                                    //cell_ck = 1;
                                    int position = dfs_col_depth((count_ai_row - (cnt_ai_stor + 1)), (count_ai_col - (cnt_ai_stor + 1)));
                                    if (position == 1) {
                                        cnt_ai_arr[z++] = cntai;
                                        cnt_ai_row_arr[x] = (count_ai_row - (cnt_ai_stor + 1));
                                        cnt_ai_col_arr[x] = (count_ai_col - (cnt_ai_stor + 1));
                                        x++;
                                    }
                                }
                            }
                            cntai = 1;
                        }
                    } else {
                        cntai = 1;
                    }
                }

                /// each single diagonal_2 check for the best position
                if (w == 1) {
                    storebest_ai = 0;
                    for (int r = 0; r < z; r++) {
                        if (r == 0) {
                            storebest_ai = cnt_ai_arr[r];
                        } else {
                            if (storebest_ai < cnt_ai_arr[r]) {
                                storebest_ai = cnt_ai_arr[r];
                            }
                        }
                    }

                    for (int r = 0; r < z; r++) { // same best move store for AI
                        if (storebest_ai != 0 && storebest_ai == cnt_ai_arr[r]) {
                            aibestmove_arr[jj] = cnt_ai_arr[r];
                            aibestrow[jj] = cnt_ai_row_arr[r];
                            aibestclm[jj] = cnt_ai_col_arr[r];
                            //Log.d( "MYINT","AI 2nd diagonal row = "+cnt_ai_row_arr[r]+" 2nd diagonal colomn = "+cnt_ai_col_arr[r]);
                            jj++;
                        }
                    }


//        for (int t = 0; t < z; t++) {
//            if (cnt_ai_arr[t] != 0 && (cnt_ai_row_arr[t] != 0 && cnt_ai_col_arr[t] != 0)) {
//                if (update_ck_ai <= cnt_ai_arr[t]) {
//                    update_ck_ai = cnt_ai_arr[t];
//                    count_ai_row_update = cnt_ai_row_arr[t];
//                    count_ai_col_update = cnt_ai_col_arr[t];
//                }
//            }
//        }
                } else {
                    storebest_human = 0;
                    for (int r = 0; r < z; r++) {
                        if (r == 0) {
                            storebest_human = cnt_ai_arr[r];
                        } else {
                            if (storebest_human < cnt_ai_arr[r]) {
                                storebest_human = cnt_ai_arr[r];
                            }
                        }
                    }

                    for (int r = 0; r < z; r++) { // same best move store for human
                        if (storebest_human != 0 && storebest_human == cnt_ai_arr[r]) {
                            humanbestmove_arr[kk] = cnt_ai_arr[r];
                            humanbestrow[kk] = cnt_ai_row_arr[r];
                            humanbestclm[kk] = cnt_ai_col_arr[r];
                            // Log.d( "MYINT","Human 2nd diagonal row = "+cnt_ai_row_arr[r]+" 2nd diagonal colomn = "+cnt_ai_col_arr[r]);
                            kk++;
                        }
                    }


//        for (int t = 0; t < z; t++) {
//            if (cnt_ai_arr[t] != 0 && (cnt_ai_row_arr[t] != 0 && cnt_ai_col_arr[t] != 0)) {
//                if (update_ck_hm <= cnt_ai_arr[t]) {
//                    update_ck_hm = cnt_ai_arr[t];
//                    count_hm_row_update = cnt_ai_row_arr[t];
//                    count_hm_col_update = cnt_ai_col_arr[t];
//                }
//            }
//        }
                }
            }

            ch = 'p';
        }

        ch = 'c';

// diagonal_3 check (from top-right to bottom-left)

        for (int w = 1; w <= 2; w++) {
            cnt_ai_hori = 0;

            for (m = 1; m <= 3; m++) {
                cntai = 1;
                x = 0;
                z = 0;
                // memset by zero
                for (int xx = 0; xx < 8; xx++) {
                    cnt_ai_arr[xx] = 0;
                    cnt_ai_row_arr[xx] = 0;
                    cnt_ai_col_arr[xx] = 0;
                }

                row = 0;
                col = 0;
                for (i = m, j = 7; i <= 6; i++, j--) {
                    cnt_ai_hori = 0;
                    if (place[i][j] == ch) {
                        while (i <= 6 && place[i][j] != '_' && place[i][j] == ch) {
                            cnt_ai_hori++;
                            i++;
                            j--;
                        }
                        if (i <= 6 && place[i][j] == '_') {
                            row = i;
                            col = j;
                            ++i;
                            --j;
                            if (i <= 6 && place[i][j] != '_' && place[i][j] == ch) {
                                while (i <= 6 && place[i][j] != '_' && place[i][j] == ch) {
                                    cnt_ai_hori++;
                                    i++;
                                    j--;
                                }
                                if (cnt_ai_hori >= 3) {
                                    int position = dfs_col_depth(row, col);
                                    if (position == 1) {
                                        if (top[col] < 6) {
                                            ++top[col];

                                            place[row][col] = 'c';
                                            count_ai_row_update = 0;
                                            count_ai_col_update = 0;
                                            count_hm_row_update = 0;
                                            count_hm_col_update = 0;
                                            updateplace_ck = 1;
                                            cnt_ai_hori = 0;

                                            updateplace();
                                            return;
                                        }
                                    }
                                } else {
                                    --i;
                                    ++j;
                                    cnt_ai_hori = 0;
                                }
                            } else {
                                --i;
                                ++j;
                                cnt_ai_hori = 0;
                            }
                        } else {
                            --i;
                            ++j;
                            cnt_ai_hori = 0;
                        }
                    }
                    row = 0;
                    col = 0;
                }

                for (i = m, j = 7; i <= 5; i++, j--) {
                    if (place[i][j] == ch) {
                        if (place[i][j] == place[i + 1][j - 1]) {
                            if (cntai == 1)
                                win[cntai] = img[i][j];
                            cntai++;
                            win[cntai] = img[i + 1][j - 1];
                        } else {
                            if (cntai <= 6) {
                                cnt_ai_stor = cntai;
                                count_ai_row = i + 1;
                                count_ai_col = j - 1;
                                if (((i + 1) <= 6 && (j - 1) > 1) && place[count_ai_row][count_ai_col] == '_') {

                                    //cell_ck = 1;
                                    int position = dfs_col_depth(count_ai_row, count_ai_col);
                                    if (position == 1) {
                                        cnt_ai_arr[z++] = cntai;
                                        cnt_ai_row_arr[x] = count_ai_row;
                                        cnt_ai_col_arr[x] = count_ai_col;
                                        x++;
                                    }
                                }
                                if ((count_ai_col + (cnt_ai_stor + 1) <= 7) && place[count_ai_row - (cnt_ai_stor + 1)][count_ai_col + (cnt_ai_stor + 1)] == '_') {
                                    //cell_ck = 1;
                                    int position = dfs_col_depth((count_ai_row - (cnt_ai_stor + 1)), (count_ai_col + (cnt_ai_stor + 1)));
                                    if (position == 1) {
                                        cnt_ai_arr[z++] = cntai;
                                        cnt_ai_row_arr[x] = (count_ai_row - (cnt_ai_stor + 1));
                                        cnt_ai_col_arr[x] = (count_ai_col + (cnt_ai_stor + 1));
                                        x++;
                                        position = 0;
                                    }
                                }
                            }
                            cntai = 1;
                        }
                    } else {
                        cntai = 1;
                    }
                }

                /// each single diagonal_3 check for the best position
                if (w == 1) {
                    storebest_ai = 0;
                    for (int r = 0; r < z; r++) {
                        if (r == 0) {
                            storebest_ai = cnt_ai_arr[r];
                        } else {
                            if (storebest_ai < cnt_ai_arr[r]) {
                                storebest_ai = cnt_ai_arr[r];
                            }
                        }
                    }

                    for (int r = 0; r < z; r++) { // same best move store for AI
                        if (storebest_ai != 0 && storebest_ai == cnt_ai_arr[r]) {
                            aibestmove_arr[jj] = cnt_ai_arr[r];
                            aibestrow[jj] = cnt_ai_row_arr[r];
                            aibestclm[jj] = cnt_ai_col_arr[r];
                            jj++;
                        }
                    }


//                for (int t = 0; t < z; t++) {
//                    if (cnt_ai_arr[t] != 0 && (cnt_ai_row_arr[t] != 0 && cnt_ai_col_arr[t] != 0)) {
//                        if (update_ck_ai <= cnt_ai_arr[t]) {
//                            update_ck_ai = cnt_ai_arr[t];
//                            count_ai_row_update = cnt_ai_row_arr[t];
//                            count_ai_col_update = cnt_ai_col_arr[t];
//                        }
//                    }
//                }
                } else {
                    storebest_human = 0;
                    for (int r = 0; r < z; r++) {
                        if (r == 0) {
                            storebest_human = cnt_ai_arr[r];
                        } else {
                            if (storebest_human < cnt_ai_arr[r]) {
                                storebest_human = cnt_ai_arr[r];
                            }
                        }
                    }

                    for (int r = 0; r < z; r++) { // same best move store for human
                        if (storebest_human != 0 && storebest_human == cnt_ai_arr[r]) {
                            humanbestmove_arr[kk] = cnt_ai_arr[r];
                            humanbestrow[kk] = cnt_ai_row_arr[r];
                            humanbestclm[kk] = cnt_ai_col_arr[r];
                            kk++;
                        }
                    }

//                for (int t = 0; t < z; t++) {
//                    if (cnt_ai_arr[t] != 0 && (cnt_ai_row_arr[t] != 0 && cnt_ai_col_arr[t] != 0)) {
//                        if (update_ck_hm <= cnt_ai_arr[t]) {
//                            update_ck_hm = cnt_ai_arr[t];
//                            count_hm_row_update = cnt_ai_row_arr[t];
//                            count_hm_col_update = cnt_ai_col_arr[t];
//                        }
//                    }
//                }
                }
            }

            ch = 'p';
        }

        twotimes = 2;
        ch = 'c';
        player = true;


        /// diagonal_4 check (from top-right to bottom-left)

        while ((twotimes--) != 0) {
            cnt_ai_hori = 0;

            for (m = 6; m >= 4; m--) {
                cntai = 1;
                x = 0;
                z = 0;
                // memset by zero
                for (int xx = 0; xx < 8; xx++) {
                    cnt_ai_arr[xx] = 0;
                    cnt_ai_row_arr[xx] = 0;
                    cnt_ai_col_arr[xx] = 0;
                }

                row = 0;
                col = 0;
                for (i = 1, j = m; j >= 1; i++, j--) {
                    cnt_ai_hori = 0;
                    if (place[i][j] == ch) {
                        while (j >= 1 && place[i][j] != '_' && place[i][j] == ch) {
                            cnt_ai_hori++;
                            i++;
                            j--;
                        }
                        if (j >= 1 && place[i][j] == '_') {
                            row = i;
                            col = j;
                            ++i;
                            --j;
                            if (j >= 1 && place[i][j] != '_' && place[i][j] == ch) {
                                while (j >= 1 && place[i][j] != '_' && place[i][j] == ch) {
                                    cnt_ai_hori++;
                                    i++;
                                    j--;
                                }
                                if (cnt_ai_hori >= 3) {
                                    int position = dfs_col_depth(row, col);
                                    if (position == 1) {
                                        if (top[col] < 6) {
                                            ++top[col];

                                            place[row][col] = 'c';
                                            count_ai_row_update = 0;
                                            count_ai_col_update = 0;
                                            count_hm_row_update = 0;
                                            count_hm_col_update = 0;
                                            updateplace_ck = 1;
                                            cnt_ai_hori = 0;

                                            updateplace();
                                            return;
                                        }
                                    }
                                } else {
                                    --i;
                                    ++j;
                                    cnt_ai_hori = 0;
                                }
                            } else {
                                --i;
                                ++j;
                                cnt_ai_hori = 0;
                            }
                        } else {
                            --i;
                            ++j;
                            cnt_ai_hori = 0;
                        }
                    }
                    row = 0;
                    col = 0;
                }

                for (i = 1, j = m; j >= 2; i++, j--) {
                    if (place[i][j] == ch) {
                        if (place[i][j] == place[i + 1][j - 1]) {
                            if (cntai == 1)
                                win[cntai] = img[i][j];
                            cntai++;
                            win[cntai] = img[i + 1][j - 1];
                        } else {
                            if (cntai <= 6) {
                                cnt_ai_stor = cntai;
                                count_ai_row = i + 1;
                                count_ai_col = j - 1;
                                if (((i + 1) <= 6 && (j - 1) >= 1) && place[count_ai_row][count_ai_col] == '_') {

                                    //cell_ck = 1;
                                    int position = dfs_col_depth(count_ai_row, count_ai_col);
                                    if (position == 1) {
                                        cnt_ai_arr[z++] = cntai;
                                        cnt_ai_row_arr[x] = count_ai_row;
                                        cnt_ai_col_arr[x] = count_ai_col;
                                        x++;
                                    }
                                }
                                if ((count_ai_row - (cnt_ai_stor + 1) >= 1) && place[count_ai_row - (cnt_ai_stor + 1)][count_ai_col + (cnt_ai_stor + 1)] == '_') {
                                    //cell_ck = 1;
                                    int position = dfs_col_depth((count_ai_row - (cnt_ai_stor + 1)), (count_ai_col + (cnt_ai_stor + 1)));
                                    if (position == 1) {
                                        cnt_ai_arr[z++] = cntai;
                                        cnt_ai_row_arr[x] = (count_ai_row - (cnt_ai_stor + 1));
                                        cnt_ai_col_arr[x] = (count_ai_col + (cnt_ai_stor + 1));
                                        x++;
                                    }

                                }
                            }
                            cntai = 1;
                        }
                    } else {
                        cntai = 1;
                    }
                }

                /// each single diagonal_4 check for the best position
                if (player) { // for AI jonno ay if use kora hoyse
                    storebest_ai = 0;
                    for (int r = 0; r < z; r++) {
                        if (r == 0) {
                            storebest_ai = cnt_ai_arr[r];
                        } else {
                            if (storebest_ai < cnt_ai_arr[r]) {
                                storebest_ai = cnt_ai_arr[r];
                            }
                        }
                    }

                    for (int r = 0; r < z; r++) { // same best move store for AI
                        if (storebest_ai != 0 && storebest_ai == cnt_ai_arr[r]) {
                            aibestmove_arr[jj] = cnt_ai_arr[r];
                            aibestrow[jj] = cnt_ai_row_arr[r];
                            aibestclm[jj] = cnt_ai_col_arr[r];
                            jj++;
                        }
                    }

                } else {
                    storebest_human = 0;
                    for (int r = 0; r < z; r++) {
                        if (r == 0) {
                            storebest_human = cnt_ai_arr[r];
                        } else {
                            if (storebest_human < cnt_ai_arr[r]) {
                                storebest_human = cnt_ai_arr[r];
                            }
                        }
                    }

                    for (int r = 0; r < z; r++) { // same best move store for human
                        if (storebest_human != 0 && storebest_human == cnt_ai_arr[r]) {
                            humanbestmove_arr[kk] = cnt_ai_arr[r];
                            humanbestrow[kk] = cnt_ai_row_arr[r];
                            humanbestclm[kk] = cnt_ai_col_arr[r];
                            kk++;
                        }
                    }
                }
            }

            player = !(player);
            ch = 'p';
        }

        // memset
        for (int ii = 0; ii < 100; ii++) {
            store_ai[ii] = 0;
            store_human[ii] = 0;
            store_ai_row[ii] = 0;
            store_ai_clm[ii] = 0;
            store_human_row[ii] = 0;
            store_human_clm[ii] = 0;
        }

        int ai = 0, human = 0, ww = 1, yy = 1;

        // for AI
        for (int pp = 1; pp < jj; pp++) {
            if (ai < aibestmove_arr[pp]) {
                ai = aibestmove_arr[pp];
            }
        }
        for (int r = 1; r < jj; r++) {
            if (ai != 0 && ai == aibestmove_arr[r]) {
                store_ai[ww] = aibestmove_arr[r];
                store_ai_row[ww] = aibestrow[r];
                store_ai_clm[ww] = aibestclm[r];
                // Log.d( "MYINT","store_ai value is = "+store_ai[ww]+" store_ai_row = "+store_ai_row[ww]+" store_ai_clm = "+store_ai_clm[ww]);
                ww++;
            }
        }

        // for human
        for (int pp = 1; pp < kk; pp++) {
            if (human < humanbestmove_arr[pp]) {
                human = humanbestmove_arr[pp];
            }
        }
        for (int r = 1; r < kk; r++) {
            if (human != 0 && human == humanbestmove_arr[r]) {
                store_human[yy] = humanbestmove_arr[r];
                store_human_row[yy] = humanbestrow[r];
                store_human_clm[yy] = humanbestclm[r];
                //Log.d( "MYINT","store_human value is = "+store_human[yy]+" store_human_row = "+store_human_row[yy]+" store_human_clm = "+store_human_clm[yy]);
                yy++;
            }
        }

//        Log.d( "MYINT","for AI jj = "+jj+" ww = "+ww);
//        Log.d( "MYINT","for human kk = "+kk+" yy = "+yy);

        if (updateplace_ck == 0 && (store_human[1] > store_ai[1]) && (store_ai[1] != 3) && (store_human[1] >= 2)) {
            //Log.d( "MYINT","store_human[1] = "+store_human[1]+" store_ai[1] = "+store_ai[1]);
            int k;
            //Log.d( "MYINT","human step yy = "+(yy-1));
            // opponent ke thekate AI best move randomly
            k = rand.nextInt(yy) + 1;
            if (k != 1) {
                k = k - 1;
            }

            // Log.d( "MYINT","human random value of k = "+k);
            //Log.d( "MYINT","store_human_row = "+store_human_row[k]+" store_human_clm = "+store_human_clm[k]);

            if ((store_human_row[k] <= 6 && store_human_clm[k] <= 7) && (store_human_row[k] != 0 && store_human_clm[k] != 0)) {
                //Log.d( "MYINT","store_human_row = "+store_human_row[k]+" store_human_clm = "+store_human_clm[k]);
                if (top[store_human_clm[k]] < 6) {

                    ++top[store_human_clm[k]];
                    // Log.d( "aimove: ","human human human human human human human human");
                    place[store_human_row[k]][store_human_clm[k]] = 'c';
                    updateplace_ck = 1;

                    updateplace();
                }
            }
        }

//        if(updateplace_ck == 0 && humanbestmove_arr[1] >= aibestmove_arr[1]){
//            int k;
//            Log.d( "MYINT","human step kk = "+kk);
//            //k = rand.nextInt(kk); // opponent ke thekate AI best move randomly
//            k = rand.nextInt(kk)+1;
//            Log.d( "MYINT","human random value of k = "+k);
//            Log.d( "MYINT","humanbest_row = "+humanbestrow[k]+" humanbest_clm = "+humanbestclm[k]);
//            if((humanbestrow[k] <= 6 && humanbestclm[k] <= 7) && (humanbestrow[k] != 0 && humanbestclm[k] != 0)){
//                Log.d( "MYINT","humanbest_row = "+humanbestrow[k]+" humanbest_clm = "+humanbestclm[k]);
//                if(top[humanbestclm[k]] < 6){
//
//                    ++top[humanbestclm[k]];
//                   // Log.d( "aimove: ","human human human human human human human human");
//                    place[humanbestrow[k]][humanbestclm[k]] = 'c';
//                    updateplace_ck=1;
//
//                    updateplace();
//                }
//            }
//        }

        else if (updateplace_ck == 0 && store_human[1] <= store_ai[1]) {
            //Log.d( "MYINT","store_human[1] = "+store_human[1]+" store_ai[1] = "+store_ai[1]);
            int k;
            //Log.d( "MYINT","AI step ww = "+(ww-1));
            // AI best move selected randomly
            k = rand.nextInt(ww) + 1;
            if (k != 1) {
                k = k - 1;
            }
            //Log.d( "MYINT","AI random value of k = "+k);
            //Log.d( "MYINT","store_ai_row = "+store_ai_row[k]+" store_ai_clm = "+store_ai_clm[k]);

            if ((store_ai_row[k] <= 6 && store_ai_clm[k] <= 7) && (store_ai_row[k] != 0 && store_ai_clm[k] != 0)) {
                //Log.d( "MYINT","store_ai_row = "+store_ai_row[k]+" store_ai_clm = "+store_ai_clm[k]);
                if (top[store_ai_clm[k]] < 6) {
                    ++top[store_ai_clm[k]];
                    //Log.d( "aimove: ","beside ai place beside ai place beside ai place");

                    place[store_ai_row[k]][store_ai_clm[k]] = 'c';
                    updateplace_ck = 1;

                    updateplace();
                }
            }
        }

//        else if(updateplace_ck == 0 && humanbestmove_arr[1] < aibestmove_arr[1]){
//            int k;
//            Log.d( "MYINT","AI step jj = "+jj);
//            //k = rand.nextInt(jj); // AI best move selected randomly
//            k = rand.nextInt(jj) + 1;
//            Log.d( "MYINT","AI random value of k = "+k);
//            Log.d( "MYINT","aibest_row = "+aibestrow[k]+" aibest_clm = "+aibestclm[k]);
//            if((aibestrow[k] <= 6 && aibestclm[k] <= 7) && (aibestrow[k] != 0 && aibestclm[k] != 0)){
//                Log.d( "MYINT","aibest_row = "+aibestrow[k]+" aibest_clm = "+aibestclm[k]);
//                if(top[aibestclm[k]] < 6) {
//                    ++top[aibestclm[k]];
//                    //Log.d( "aimove: ","beside ai place beside ai place beside ai place");
//
//                    place[aibestrow[k]][aibestclm[k]] = 'c';
//                    updateplace_ck = 1;
//
//                    updateplace();
//                }
//            }
//        }

        /// ai random move
        if (updateplace_ck == 0) {
            // Log.d( "aimove: ","akhono updateplace_ck = 0 and random move");
            updateplace_ck = 1;
            // choose any random number
            int fl = 0, t, k;
            do {
                k = rand.nextInt(7) + 1;
                // Log.d( "MYINT","only random value of k = "+k);
                if (top[k] < 6) {
                    t = ++top[k];
                    if (place[t][k] == '_') {
                        place[t][k] = 'c';
                        updateplace();
                        fl = 1;
                    }
                }
            } while (fl == 0);
        }
    }


    public void draw() {
        TextView txt = (TextView) findViewById(R.id.textView2);
        txt.setText("GAME OVER- DRAW!!");

        txt.setBackgroundColor(Color.rgb(255, 255, 102));

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2500);
    }


    void winner() {
        TextView txt = (TextView) findViewById(R.id.textView2);
        if (playerwon == 'c') {
            txt.setText("AI WON!!!");
            txt.setTextColor(Color.rgb(255, 0, 127));
        } else if (playerwon == 'p') {
            txt.setText("HUMAN WON!!!");
            txt.setTextColor(Color.rgb(0, 0, 153));
        }

        txt.setBackgroundColor(Color.rgb(51, 255, 153));

        for (int i = 1; i <= 4; i++)
            win[i].setImageResource(R.drawable.decor_4);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2500);


    }


    Boolean check() {
        int i, j;
        int cnt = 1;

        //checking draw

        int fl = 0;

        for (i = 1; i <= 6; i++) {
            for (j = 1; j <= 7; j++) {
                if (place[i][j] == '_') {
                    fl = 1;
                    break;
                }
            }

        }

        if (fl == 0)
            draw();

        //checking Horizontal row

        for (i = 1; i <= 6; i++) {
            cnt = 1;
            for (j = 2; j <= 7; j++) {
                if (place[i][j - 1] != '_') {
                    if (place[i][j - 1] == place[i][j]) {
                        if (cnt == 1)
                            win[cnt] = img[i][j - 1];
                        cnt++;
                        win[cnt] = img[i][j];
                    } else
                        cnt = 1;
                } else
                    cnt = 1;

                if (cnt == 4) {

                    playerwon = place[i][j - 1];
                    winner();
                    return true;
                }


            }
        }
        cnt = 1;

        //checking Vertical

        for (j = 1; j <= 7; j++) {
            cnt = 1;
            for (i = 2; i <= 6; i++) {
                if (place[i - 1][j] != '_') {
                    if (place[i - 1][j] == place[i][j]) {
                        if (cnt == 1)
                            win[cnt] = img[i - 1][j];
                        cnt++;
                        win[cnt] = img[i][j];


                    } else
                        cnt = 1;
                } else
                    cnt = 1;

                if (cnt == 4) {

                    playerwon = place[i - 1][j];
                    winner();
                    return true;
                }

            }
        }

        cnt = 0;


        int x = 2, k;
        char p = 'c';
        //checking diagonals for each player
        while ((x--) != 0) {

            cnt = 0;
            // diagonal_2 check (from top-left to bottom-right)
            for (k = 4; k >= 1; k--) {
                cnt = 0;
                for (i = 1, j = k; j <= 7 && i <= 6; i++, j++) {
                    if (place[i][j] == p) {
                        cnt++;
                        win[cnt] = img[i][j];
                    } else
                        cnt = 0;

                    if (cnt == 4) {

                        playerwon = p;
                        winner();
                        return true;
                    }

                }
            }

            // diagonal_4 check (from top-right to bottom-left)
            for (k = 4; k <= 7; k++) {
                cnt = 0;
                for (i = 1, j = k; j >= 1 && i <= 6; i++, j--) {
                    if (place[i][j] == p) {
                        cnt++;
                        win[cnt] = img[i][j];
                    } else
                        cnt = 0;

                    if (cnt == 4) {

                        playerwon = p;
                        winner();

                        return true;
                    }
                }
            }

            // diagonal_1 check (from top-left to bottom-right)
            for (int m = 2; m <= 3; m++) {
                cnt = 0;
                for (i = m, j = 1; i <= 6; i++, j++) {
                    if (place[i][j] == p) {
                        cnt++;
                        win[cnt] = img[i][j];
                    } else
                        cnt = 0;

                    if (cnt == 4) {

                        playerwon = p;
                        winner();

                        return true;
                    }
                }
            }

            // diagonal_3 check (from top-right to bottom-left)
            for (int m = 2; m <= 3; m++) {
                cnt = 0;
                for (i = m, j = 7; i <= 6; i++, j--) {
                    if (place[i][j] == p) {
                        cnt++;
                        win[cnt] = img[i][j];
                    } else
                        cnt = 0;

                    if (cnt == 4) {

                        playerwon = p;
                        winner();

                        return true;
                    }
                }
            }

            p = 'p';

        }

        return false;

    }

    public void updateplace() {


        img[1][1] = (ImageView) findViewById(R.id.iv11);
        img[1][2] = (ImageView) findViewById(R.id.iv12);
        img[1][3] = (ImageView) findViewById(R.id.iv13);
        img[1][4] = (ImageView) findViewById(R.id.iv14);
        img[1][5] = (ImageView) findViewById(R.id.iv15);
        img[1][6] = (ImageView) findViewById(R.id.iv16);
        img[1][7] = (ImageView) findViewById(R.id.iv17);
        img[2][1] = (ImageView) findViewById(R.id.iv21);
        img[2][2] = (ImageView) findViewById(R.id.iv22);
        img[2][3] = (ImageView) findViewById(R.id.iv23);
        img[2][4] = (ImageView) findViewById(R.id.iv24);
        img[2][5] = (ImageView) findViewById(R.id.iv25);
        img[2][6] = (ImageView) findViewById(R.id.iv26);
        img[2][7] = (ImageView) findViewById(R.id.iv27);
        img[3][1] = (ImageView) findViewById(R.id.iv31);
        img[3][2] = (ImageView) findViewById(R.id.iv32);
        img[3][3] = (ImageView) findViewById(R.id.iv33);
        img[3][4] = (ImageView) findViewById(R.id.iv34);
        img[3][5] = (ImageView) findViewById(R.id.iv35);
        img[3][6] = (ImageView) findViewById(R.id.iv36);
        img[3][7] = (ImageView) findViewById(R.id.iv37);
        img[4][1] = (ImageView) findViewById(R.id.iv41);
        img[4][2] = (ImageView) findViewById(R.id.iv42);
        img[4][3] = (ImageView) findViewById(R.id.iv43);
        img[4][4] = (ImageView) findViewById(R.id.iv44);
        img[4][5] = (ImageView) findViewById(R.id.iv45);
        img[4][6] = (ImageView) findViewById(R.id.iv46);
        img[4][7] = (ImageView) findViewById(R.id.iv47);
        img[5][1] = (ImageView) findViewById(R.id.iv51);
        img[5][2] = (ImageView) findViewById(R.id.iv52);
        img[5][3] = (ImageView) findViewById(R.id.iv53);
        img[5][4] = (ImageView) findViewById(R.id.iv54);
        img[5][5] = (ImageView) findViewById(R.id.iv55);
        img[5][6] = (ImageView) findViewById(R.id.iv56);
        img[5][7] = (ImageView) findViewById(R.id.iv57);
        img[6][1] = (ImageView) findViewById(R.id.iv61);
        img[6][2] = (ImageView) findViewById(R.id.iv62);
        img[6][3] = (ImageView) findViewById(R.id.iv63);
        img[6][4] = (ImageView) findViewById(R.id.iv64);
        img[6][5] = (ImageView) findViewById(R.id.iv65);
        img[6][6] = (ImageView) findViewById(R.id.iv66);
        img[6][7] = (ImageView) findViewById(R.id.iv67);

        int i, j;
        String s;
        StringBuilder sb = new StringBuilder();
        for (i = 1; i <= 6; i++)
            for (j = 1; j <= 7; j++) {
                sb.append(place[i][j]);
                sb.append(',');
            }
        s = sb.toString();

// just check printf type
        Log.i("just check code", s);

        for (i = 1; i <= 6; i++)
            for (j = 1; j <= 7; j++) {
                if (place[i][j] == 'p')
                    img[i][j].setImageResource(R.drawable.blueround);
                else if (place[i][j] == '_')
                    img[i][j].setImageResource(R.drawable.blackround);
                else
                    img[i][j].setImageResource(R.drawable.redround);
            }

        check();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_player_activity);

        int i, j;
        for (i = 1; i <= 6; i++)
            for (j = 1; j <= 7; j++)
                place[i][j] = '_';

        for (i = 1; i <= 7; i++)
            top[i] = 0;


        Context context = getApplicationContext();
        CharSequence text = "Start playing! Human's Turn";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();


    }


    public void insert1(View v) {
        TextView tt = (TextView) findViewById(R.id.textView2);
        int t;

        if (top[1] < 6) {
            top[1]++;
            t = top[1];

            tt.setText(" Human Turn Now!");
            place[t][1] = 'p';
            updateplace();

            // AI Turn now
            aimove();
            // tt.setText("Human Turn Now!");

        }
    }

    public void insert2(View v) {
        TextView tt = (TextView) findViewById(R.id.textView2);
        int t;
        if (top[2] < 6) {
            top[2]++;
            t = top[2];
            tt.setText(" Human Turn Now!");
            place[t][2] = 'p';

            updateplace();
            // AI Turn now
            aimove();
            // tt.setText(" Human Turn Now!");
        }
    }

    public void insert3(View v) {
        TextView tt = (TextView) findViewById(R.id.textView2);
        int t;
        if (top[3] < 6) {
            t = ++top[3];
            tt.setText(" Human Turn Now!");
            place[t][3] = 'p';
            updateplace();
            // AI Turn now
            aimove();
            //  tt.setText(" Human Turn Now!");

        }
    }

    public void insert4(View v) {
        TextView tt = (TextView) findViewById(R.id.textView2);
        int t;
        if (top[4] < 6) {
            t = ++top[4];
            tt.setText(" Human Turn Now!");
            place[t][4] = 'p';

            updateplace();
            // AI Turn now
            aimove();
            //tt.setText(" Human Turn Now!");

        }
    }

    public void insert5(View v)

    {
        TextView tt = (TextView) findViewById(R.id.textView2);
        int t;
        if (top[5] < 6) {
            t = ++top[5];
            tt.setText(" Human Turn Now!");
            place[t][5] = 'p';

            updateplace();
            // AI Turn now
            aimove();
            //tt.setText("Human Turn Now!");
        }

    }

    public void insert6(View v) {
        TextView tt = (TextView) findViewById(R.id.textView2);
        int t;
        if (top[6] < 6) {
            t = ++top[6];
            tt.setText(" Human Turn Now!");
            place[t][6] = 'p';

            updateplace();
            // AI Turn now
            aimove();
            //tt.setText("Human Turn Now!");
        }
    }

    public void insert7(View v) {
        TextView tt = (TextView) findViewById(R.id.textView2);
        int t;
        if (top[7] < 6) {
            t = ++top[7];
            tt.setText(" Human Turn Now!");
            place[t][7] = 'p';

            updateplace();
            // AI Turn now
            aimove();
            // tt.setText("Human Turn Now!");
        }
    }
}
