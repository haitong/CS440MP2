
class Semester
{
  private:
    bool is_fall;
    int _satisfied;
  public:
    Semester()
    {
      is_fall = true;
      _satisfied = 0;
    }
    bool isFall() { return is_fall; }
    bool isSpring() { return !is_fall; }
    int getSatisfied() { return _satisfied; }
    void addSatisfied( int credits)
    {
      _satisfied += credits;
    }

};
