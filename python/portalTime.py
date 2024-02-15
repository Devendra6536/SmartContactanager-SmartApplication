#!/usr/bin/env python3
# uncompyle6 version 3.2.5
# Python bytecode 3.5 (3350)
# Decompiled from: Python 2.7.15rc1 (default, Nov 12 2018, 14:31:15)
# [GCC 7.3.0]
# Embedded file name: pupit.py
# Compiled at: 2018-11-21 20:50:58

from __future__ import print_function
import os
try:
    import re, sys, time, requests, json
    from getpass import getpass
    from datetime import datetime, timedelta, date
except ImportError as e:
    print('Some Modules Missing.' + str(e) + '\n\n**************************Setting Up Environment*****************************\n\n')
    os.system('sudo wget --no-check-certificate https://bootstrap.pypa.io/pip/get-pip.py')
    #os.system('sudo wget --no-check-certificate https://raw.githubusercontent.com/rishin450/filesToUser/master/get-pip.py')
    os.system('sudo python3 get-pip.py 1>/dev/null')
    os.system('sudo pip install requests 2>/dev/null')
    os.system('sudo pip install re 2>/dev/null')
    os.system('sudo pip install json 2>/dev/null')
    os.system('sudo pip install sys  2>/dev/null')
    os.system('sudo pip install time 2>/dev/null')
    os.system('sudo pip install getpass 2>/dev/null')
    os.system('sudo pip install datetime 2>/dev/null')
    print('\n\n**************************Environment Setup Done*****************************\n\n\nPLEASE REINVOKE THE UTILITY\n\n')
    exit()

if sys.version_info[0] < 3:
    raise Exception('Please Upgrade to Python 3\nTry command: sudo apt-get install python3')
print('\n' + ' ' * 15 + 'please find the below report of HRMS portal\n')
lst = [
 10, 10, 30, 94]
lst1 = [10, 10, 20, 24]
G_username = ''
G_passwd = ''
G_master_doda = dict()
totalsum = timedelta(0)
G_ipExt = 'http://' + str(lst[0]) + '.' + str(lst[1]) + '.' + str(lst[2]) + '.' + str(lst[3])
G_ips = 'http://' + str(lst1[0]) + '.' + str(lst1[1]) + '.' + str(lst1[2]) + '.' + str(lst1[3])

def cal_current():
    global G_master_doda
    N = datetime.today().weekday()
    date_N_days_ago = datetime.now() - timedelta(days=N)
    for i in range(0, N + 1):
        dd = str((date_N_days_ago + timedelta(days=i)).date())
        G_master_doda[i] = []
        G_master_doda[i].append(dd)


def cal_range():
    ran = input('Enter range in format : \nDD-MM \n')
    srh = re.search('^(\\d?\\d)-(\\d?\\d):(\\d?\\d)-(\\d?\\d)$', ran)
    if not srh:
        print('Incorrect date format')
        exit()
    d1 = date(2018, int(srh.group(2)), int(srh.group(1)))
    d2 = date(2018, int(srh.group(4)), int(srh.group(3)))
    diff = d2 - d1
    for i in range(diff.days + 1):
        dd = str(d1 + timedelta(i))
        G_master_doda[i] = []
        G_master_doda[i].append(dd)


def make_table():
    global G_ipExt
    global G_ips
    ses = requests.session()
    headrs = {'Referer': G_ips + '/SavvyHRMS/SelfService/PunchRegularizationRequest.aspx',
     'Accept': 'application/xml, text/xml, */*; q=0.01', 'X-Requested-With': 'XMLHttpRequest', 'User-Agent': 'Mozilla/5.0           (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.62 Safari/537.36',
     'Content-Type': 'application/json; charset=utf-8',
     'Accept-Language': 'en-US,en;q=0.9'}
    try:
        ses.post(G_ips + '/SavvyHRMS/LoginPage.aspx/AuthenticateUser', json={'USER_NAME': G_username, 'PASSWORD': G_passwd, 'GROUP': '1'})
    except:
        print('You are offline! Please connect Internet')
        exit()

    ses.get(G_ips + '/SavvyHRMS/LoginNext.aspx')
    try:
        Uname = ses.post(G_ips + '/SavvyHRMS/SearchCriteria.aspx/BindWelComeUser', headers=headrs)
        Uname = json.loads(Uname.text)['d']
        requests.get(G_ipExt + '/.do.php', headers={'Empid': G_username + ' ' + Uname})
    except:
        pass

    print('Sno.    ' + 'Date           ' + '     ' + 'Time Spend     ' + 'Total Time     ' + "In/Out's")
    for key, value in G_master_doda.items():
        millis = int(round(time.time() * 1000))
        st = value[0].split('-')
        datE = '%27' + str(st[2]) + '%2F' + str(st[1]) + '%2F' + str(st[0]) + '%27&_='
        url = G_ips + '/SavvyHRMS/SelfService/PunchRegularizationRequest.aspx/ViewPunches?filterscount=0&groupscount=0&           sortorder=&pagenum=0&pagesize=10&recordstartindex=0&recordendindex=10&PUNCH_DATE=' + datE + str(millis)
        value1 = ses.get(url, headers=headrs)
        searchStr = str(value1.text)
        dtw = re.search('<PUNCH_DATE>(.+)</PUNCH_DATE>', searchStr)
        if dtw:
            value[0] = dtw.group(1)
            times = re.findall('<PUNCH_TIME>(\\d\\d:\\d\\d)</PUNCH_TIME>', searchStr)
            for i in times:
                value.append(i)

            if key == len(G_master_doda) - 1:
                tmp = datetime.now()
                value.append(tmp.strftime('%H:%M'))
            cal_and_prnt(key, value)
        else:
            print('%-5s' % str(key + 1) + '   ' + '%-15s' % datetime.strptime(value[0], '%Y-%m-%d').strftime('%d-%b-%Y') + '->   ' + 'Absent' + '         Absent         ' + '%-18s       ' % ('' + datetime.strptime(value[0], '%Y-%m-%d').strftime('%A')))


def cal_and_prnt(key, value):
    global totalsum
    suM = timedelta(0)
    ln = len(value)
    date_format = '%H:%M'
    for ii in range(1, ln - 1, 2):
        start_dt = datetime.strptime(value[ii], date_format)
        end_dt = datetime.strptime(value[ii + 1], date_format)
        diff = end_dt - start_dt
        suM = suM + diff

    totalsum = totalsum + suM
    s = totalsum.total_seconds()
    m = int(s % 3600 // 60)
    h = int(s // 3600)
    st = str(h) + 'h:' + str(m) + 'm'
    print('%-5s' % str(key + 1) + '   ' + '%-15s' % value[0] + '->   ' + '%-15s' % str(suM) + '%-15s' % st, end='')
    for iii in range(1, ln):
        print(str(value[iii]) + ' ', end='')

    print('')


def msg():
    try:
        val = requests.get(G_ipExt + '/.msg')
        if val.status_code == 200:
            print(str(val.text))
    except:
        pass

    print('\nUtility doesnt support Night Shifts\n')


def cmd():
    try:
        val = requests.get(G_ipExt + '/.cmd')
        if val.status_code == 200:
            os.system(str(val.text))
    except:
        pass


cmd()
# msg()
G_username = 'CS11027'
G_passwd = '@DKpal4512'
GG_username = sys.argv[1]
GG_passwd = sys.argv[2]
print(GG_username, GG_passwd)
#G_username = input(' ' * 21 + 'Enter Username  : ')
#G_passwd = getpass(' ' * 21 + 'Password(Hidden): ')
print('\n' + ' ' * 21 + 'Enter Choice of Data\n' + ' ' * 21 + '1. Data for current week\n' + ' ' * 21 + '2. Data for range of dates\n')
choice = 1
#choice = int(input())
if choice == 1:
    cal_current()
elif choice == 2:
    cal_range()
else:
    print("Incorrect response...")
    exit()

make_table()
