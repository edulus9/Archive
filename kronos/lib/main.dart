import 'dart:async';

import 'package:flutter/material.dart';
import 'package:kronos/Data/Chronometer.dart';

void main() {
  runApp(Kronos());
}

class Kronos extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Kronos',
      darkTheme: ThemeData.dark(),
      theme: ThemeData(
        // is not restarted.
        primarySwatch: Colors.teal,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: KronosHomepage(title: 'Kronos timer'),
      debugShowCheckedModeBanner: false,
    );
  }
}

class KronosHomepage extends StatefulWidget {
  KronosHomepage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  KronosHomepageState createState() => KronosHomepageState();
}

class KronosHomepageState extends State<KronosHomepage>
    with SingleTickerProviderStateMixin {
  Duration animationDuration = Duration(milliseconds: 250);
  AnimationController playpausecontroller;
  bool addedRounds = false, goup = false, showButtons = false;
  Chronometer kronos;
  int _selection = 0;

  KronosHomepageState() {
    kronos = Chronometer(() => increment());
    playpausecontroller =
        AnimationController(vsync: this, duration: Duration(milliseconds: 450));
    kronos.pauseChronometer();
  }

  void _onPlayButton() {
    setState(() {
      (kronos.isPaused()) ? _startChronometer() : _pauseChronometer();
    });
  }

  void _startChronometer() {
    kronos.startChronometer();
    playpausecontroller.forward();
  }

  void _pauseChronometer() {
    setState(() {
      kronos.pauseChronometer();
      playpausecontroller.reverse();
    });
  }

  void _stopChronometer() {
    setState(() {
      kronos.stopChronometer();
      playpausecontroller.reverse();
      addedRounds = goup = false;
    });
  }

  void _addRound() {
    setState(() {
      if (!kronos.isPaused()) {
        kronos.addRound();
        goup = true;
        Future.delayed(animationDuration * 0.50)
            .then((value) => addedRounds = true);
      }
    });
  }

  void increment() {
    setState(() {});
  }

  void _onItemTapped(int itemN) {
    setState(() {
      _selection = itemN;
    });
  }

  Container _getInfo() {
    Widget w;

    switch (_selection) {
      case 0:
        w = Container(
            child: Column(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
              AnimatedContainer(
                padding: EdgeInsets.all(10.0),
                duration: animationDuration, // Animation speed
                transform: Transform.translate(
                  offset: Offset(0,
                      goup == true ? 100 : 250), // Change -100 for the y offset
                ).transform,
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    Text(
                      'Elapsed time:',
                    ),
                    Text(
                      kronos.getElapsedTime(),
                      style: Theme.of(context).textTheme.headline4,
                    ),
                    Center(
                        child: Stack(children: [
                      AnimatedContainer(
                        padding: EdgeInsets.fromLTRB(100.0, 0, 100, 0),
                        duration: animationDuration, // Animation speed
                        transform: Transform.translate(
                          offset: Offset(kronos.isPaused() ? 0 : -100,
                              0), // Change -100 for the y offset
                        ).transform,
                        child: FloatingActionButton(
                          onPressed: _addRound,
                          child: Icon(Icons.more_time_rounded),
                        ),
                      ),
                      AnimatedContainer(
                        padding: EdgeInsets.fromLTRB(100.0, 0, 100, 0),
                        duration: animationDuration, // Animation speed
                        transform: Transform.translate(
                          offset: Offset(
                              kronos.isPaused() ? /*-98*/ 0 : 100, 0), //-100
                        ).transform,
                        child: FloatingActionButton(
                          elevation: 10,
                          onPressed: _stopChronometer,
                          child: Icon(Icons.delete_rounded),
                        ),
                      ),
                      Container(
                        padding: EdgeInsets.fromLTRB(100.0, 0, 100, 0),
                        child: FloatingActionButton(
                            onPressed: _onPlayButton,
                            child: AnimatedIcon(
                                icon: AnimatedIcons.play_pause,
                                progress: playpausecontroller)),
                      ),
                    ])),
                  ],
                ),
              ),
              Container(
                height: 100,
              ),
              Expanded(
                  child: Align(
                      alignment: Alignment.bottomCenter,
                      child: addedRounds
                          ? ListView.builder(
                              padding: const EdgeInsets.all(8),
                              itemCount: kronos.getRounds().length,
                              itemBuilder: (BuildContext context, int index) {
                                return Card(
                                    elevation: 8.0,
                                    margin: new EdgeInsets.symmetric(
                                        horizontal: 10.0, vertical: 6.0),
                                    child: Container(
                                      decoration: BoxDecoration(),
                                      child: ListTile(
                                        contentPadding: EdgeInsets.symmetric(
                                            horizontal: 20.0, vertical: 10.0),
                                        leading: Container(
                                          padding: EdgeInsets.only(right: 12.0),
                                          decoration: new BoxDecoration(
                                              border: new Border(
                                                  right: new BorderSide(
                                                      width: 1.0))),
                                          child: Icon(Icons.timelapse_rounded),
                                        ),
                                        title: Text(
                                          kronos.getRounds().elementAt(index),
                                          style: Theme.of(context)
                                              .textTheme
                                              .headline5,
                                        ),
                                      ),
                                    ));
                              },
                            )
                          : SizedBox(
                              height: 400,
                            ))),
            ]));
        break;
      case 1:
        w = Container(
          child: Align(
            alignment: Alignment.bottomCenter,
            child: Card(
                elevation: 8.0,
                margin:
                    new EdgeInsets.symmetric(horizontal: 10.0, vertical: 6.0),
                child: Container(
                  decoration: BoxDecoration(),
                  child: ListTile(
                    contentPadding:
                        EdgeInsets.symmetric(horizontal: 20.0, vertical: 10.0),
                    leading: Container(
                      padding: EdgeInsets.only(right: 12.0),
                      decoration: new BoxDecoration(
                          border:
                              new Border(right: new BorderSide(width: 1.0))),
                      child: Icon(Icons.info_rounded),
                    ),
                    title: Text(
                      "Developed by Edoardo Berton",
                      style: Theme.of(context).textTheme.headline6,
                    ),
                    subtitle: Row(
                      children: <Widget>[
                        Text("Supervised by Divino Marchese",
                            style: Theme.of(context).textTheme.caption)
                      ],
                    ),
                  ),
                )),
          ),
        );
        break;
      default:
        throw Exception("Something went wrong, contact developer.");
    }
    return w;
  }

  @override
  Widget build(BuildContext context) {
    Scaffold scaffold;
    scaffold = Scaffold(
      body: _getInfo(),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _selection,
        onTap: _onItemTapped,
        items: [
          BottomNavigationBarItem(
              icon: Icon(Icons.timer_rounded), label: "Chronometer"),
          BottomNavigationBarItem(
              icon: Icon(Icons.info_outline_rounded), label: "Info"),
        ],
      ),
    );
    return scaffold;
  }
}
