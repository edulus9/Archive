#include <iostream>
#include <cstdlib>
#include <array>
using namespace std;

static void encrypt(int code[8]){
    for(int i=0; i<8; i++){
        if(code[i]%2==0){
            code[i]=code[i]/2;
        }else{
            code[i]=code[i]+7;
        }
    }
}

static void evenFirst(int arr[12]){
    cout << endl << "Even: ";
    for(int i=0; i<12; i+=2){
        cout << arr[i] << "; ";
    }

    cout << endl << "Odd: ";
    for(int i=1; i<12; i+=2){
        cout << arr[i] << "; ";
    }
}

static void arrout(int *arr, int len){
    cout << "{ ";
    for(int i=0; i<len; i++){
        if(i==len-1) cout << arr[i]; else cout << arr[i] << "; ";
    }
    cout << " }";
}

static void fourfiveUtil(int *arr, int len){
    int nFour=0, sumFive=0;
    for(int i=0; i<len; i++){
        if(arr[i]%10==4){
            cout << endl << "Found element ending with 4 in position " << i << ": " << arr[i];
            nFour++;
        }else if(arr[i]%10==5){
            cout << endl << "Found element ending with 5 in position " << i << ": " << arr[i];
            sumFive+=arr[i];
        }
    }
    cout << endl << endl << "Elements ending with 4: " << nFour;
    cout << endl << "Sum of elements ending with 5: " << sumFive;
}

#define codeLength 8
#define evenLength 12

int main(){
    int choice=1, arrLength=0;
    int encryptCode[codeLength], evenArray[evenLength];
    bool isInit=false;

    do{
    cout << endl;
    cout << endl << "        ╔════════════════════════════════════════════════════╗";
    cout << endl << "        ║                                                    ║";
    cout << endl << "        ║               «««« ArrayUtility »»»»               ║";
    cout << endl << "        ║         2018/2019 ITIS C. Zuccante, Mestre         ║";
    cout << endl << "        ║                                                    ║";
    cout << endl << "        ║   0. Exit                                          ║";
    cout << endl << "        ║   1. Encrypt array                                 ║";
    cout << endl << "        ║   2. EvenFirst                                     ║";
    cout << endl << "        ║   3. Five and Four locator                         ║";
    cout << endl << "        ║                                                    ║";
    cout << endl << "        ╚════════════════════════════════════════════════════╝";

        cout << endl << "Digita la tua scelta: ";
        cin >> choice;
        cout << endl;
        switch (choice){
            case 0:
                cout << "******SHUTTING DOWN******" << endl;
            break;

            case 1:
                cout << "Insert code to encrypt: ";
                for(int i=0; i<codeLength; i++){
                    cout << "Insert position " << i << ": ";
                    cin >> encryptCode[i];
                    cout << endl;
                }

                arrout(encryptCode, codeLength);
                cout << "Encrypting..." << endl << "Encrypted array: ";
                encrypt(encryptCode);
                arrout(encryptCode, codeLength);
            break;

            case 2:
                for(int i=0; i<evenLength; i++){
                    cout << "Insert position " << i << ": ";
                    cin >> evenArray[i];
                }
                evenFirst(evenArray);
            break;

            case 3:
                if(!isInit){
                    cout << "Insert array length: ";
                    cin >> arrLength;
                    while(arrLength<10 || arrLength>90){
                        cout << "****LENGTH OUT OF BOUNDS!****";
                        cout << endl << "Insert a number between 10 and 90!";
                        cout << endl << "Insert array length: ";
                        cin >> arrLength;
                    }
                    int arr[arrLength];
                    for(int i=0; i<arrLength; i++){
                        cout << "Insert position " << i << ": ";
                        cin >> arr[i];
                    }
                    fourfiveUtil(arr, arrLength);
                    isInit=true;
                }else cout << "****ARRAY ALREADY INITIALIZED!****";
                

            break;

            default:
                cout << "*****INPUT ERROR*****" << endl
                    << "Insert numbers from 0 to 3!";
            break;
            //sistema di scelta multipla
        }
    }while(choice!=0);

    return 0;
}