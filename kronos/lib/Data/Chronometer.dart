import 'dart:async';

class Chronometer{
  Duration _time = Duration(seconds: 0);
  StreamSubscription _sub;
  List<String> _rounds = List();
  var _tickFunction;
  bool _isCanceled;

  Chronometer(fun){
    _sub=_ticker().listen(null);
    stopChronometer();
    _tickFunction=fun;
  }


  Stream<bool> _ticker() async* {
    while (true) {
      await Future.delayed(Duration(seconds: 1));
      yield true;
    }
  }

  void startChronometer(){
    _isCanceled=false;
    _sub = _ticker().listen(
          (event) {
        _increment();
      },
    );
  }

  void stopChronometer(){
    _sub.cancel();
    _isCanceled=true;
    _rounds = List();
    _time = Duration(seconds: 0);
  }

  void pauseChronometer(){

    _sub.pause();

  }

  String getElapsedTime() {
    return _time.toString().substring(0, 7);
  }

  bool isPaused() =>_sub.isPaused || _isCanceled;


  void addRound() {
        _rounds.insert(0, getElapsedTime());
  }

  List<String> getRounds(){
    return _rounds;
  }

  void _increment() {
      _time = _time + Duration(seconds: 1);
      _tickFunction();
  }



}