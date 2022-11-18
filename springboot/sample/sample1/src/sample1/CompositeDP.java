package sample1;

import java.util.*;


class CompositeDP {

}


interface ValueContainer extends Iterable<Integer> {}

class SingleValue implements ValueContainer
{
  public int value;

// please leave this constructor as-is
  public SingleValue(int value)
  {
    this.value = value;
  }

@Override
public Iterator<Integer> iterator() {
	// TODO Auto-generated method stub
	return Collections.singleton(this.value).iterator();
}
}

class ManyValues extends ArrayList<Integer> implements ValueContainer
{
}


class MyList extends ArrayList<ValueContainer>
{
    // please leave this constructor as-is
  public MyList(Collection<? extends ValueContainer> c)
  {
    super(c);
  }

  public int sum()
  {
      int sum = 0;
    while(this.iterator().hasNext()){
    	ValueContainer vc = this.iterator().next();
        while(vc.iterator().hasNext()){
            sum += vc.iterator().next();
        }
    }
    return sum;
  }
}