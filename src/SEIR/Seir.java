package SEIR;


import java.util.Scanner;

    public class Seir {
        private int period;
        private double delta_t, t5, t6;
        private double s,e,i,r;
        private double alpah, beta, gamma, mu;

        public Seir(int period, double delta_t, double s, double e, double i, double r, double alpah, double beta, double gamma, double mu){
            this.period = period;
            this.delta_t = delta_t;
            this.s = s;
            this.e = e;
            this.i = i;
            this.r = r;
            this.alpah =alpah;
            this.beta = beta;
            this.gamma = gamma;
            this.mu = mu;
            t5 = 0.5*delta_t;
            t6 = 0.6*delta_t;
            double r0 = beta * s / gamma;
            if(r0 > 3){
                Scanner sc = new Scanner(System.in);
                System.out.println("<WARNING> R0 VALUE IS TOO HIGH");
                System.out.println("<WARNING> COUNTINUE (Y/N)");
                char temp = sc.next().toUpperCase().charAt(0);
                if(temp == 'N'){
                    System.out.println("<ERROR> FATAL ERROR OCCURED");
                    System.exit(-1);
                }
                sc.close();
            }
            System.out.println("R0 : " + r0);
        }

        public double[][] find_answer(){
            int count = set_count();
            double[][] res = new double[5][count];
            for(int i = 0; i < count; ++i){
                set_data(res, i);
            }
            set_day(res, count);
            return res;
        }

        private int set_count(){
            double temp = period/delta_t;
            if(temp % 1 != 0){
                return (int)temp+1;
            }
            return (int)temp;
        }

        private void set_data(double[][] res, int index){
            RK4 rk4 = new RK4();
            double[] temp = rk4.rk4();
            for(int i = 0; i < 4; ++i){
                res[i][index] = temp[i];
            }
        }

        private void set_day(double[][] res, int count){
            for(int i = 0; i < count; ++i){
                res[4][i] = delta_t * i;
            }
        }

        class RK4{
            private double[] ks,ke,ki,kr;
            private double temps = s, tempe = e, tempi = i, tempr = r;

            public double[] rk4(){
                cal();
                return toArr();
            }

            private double[] toArr(){
                double[] res = {s,e,i,r};
                return res;
            }

            private void cal(){
                init();
                for(int i = 0; i < 4; ++i){
                    k(i);
                }
                res();
            }

            private void res(){
                s += t6*sum(ks);
                e += t6*sum(ke);
                i += t6*sum(ki);
                r += t6*sum(kr);
            }

            private double sum(double[] arr){
                double res = 0;
                for(double a : arr){
                    res += a;
                }
                return res;
            }

            private void k(int i){
                ks[i] = ks(temps, tempi);
                ke[i] = ke(temps, tempe, tempi);
                ki[i] = ki(tempe, tempi);
                kr[i] = kr(tempi, tempr);
                if(i < 3){
                    temps = s + ks[i] * t5;
                    tempe = e + ke[i] * t5;
                    tempi = i + ki[i] * t5;
                    tempr = r + kr[i] * t5;
                }
            }

            private double ks(double s, double i){
                return mu-beta*s*i-mu*s;
            }

            private double ke(double s, double e, double i){
                return beta*s*i-alpah*e-mu*e;
            }

            private double ki(double e, double i){
                return alpah*e-gamma*i-mu*i;
            }

            private double kr(double i, double r){
                return gamma*i-mu*r;
            }

            private void init(){
                ks = new double[4];
                ke = new double[4];
                ki = new double[4];
                kr = new double[4];
            }
        }
    }
