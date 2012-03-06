package classify;

import io.DataSet;
import java.util.*;

import edu.stanford.nlp.ling.Word;

public class ReviewDatum implements LabeledDatum<Integer,Integer> {
	public static final int UnknownLabel = -1;
	
	private String[] ReviewTokens;
	private int[] Indices;
	private int Label, Index;
	
	public ReviewDatum(Word[] reviewTokens, int label, int index)
	{
		this.Label = label;
		this.ReviewTokens = new String[ reviewTokens.length ];
		for (int i=0; i<reviewTokens.length; i++)
			this.ReviewTokens[i] = reviewTokens[i].word ();
		this.Index = index;
	}
	
	public ReviewDatum(Word[] ReviewTokens, int label, int itemNo, int[] indices)
	{
		this(ReviewTokens,label,itemNo);
		assert ReviewTokens.length == indices.length;
		this.Indices = new int[ indices.length ];
		for(int i=0; i<indices.length; i++)
			this.Indices[i] = indices[i];
	}
	
	
	public ReviewDatum(String[] reviewTokens, int label, int index)
	{
		this.Label = label;
		this.ReviewTokens = reviewTokens;
		this.Index = index;
	}
	
	public ReviewDatum(String[] ReviewTokens, int label, int itemNo, int[] indices)
	{
		this(ReviewTokens,label,itemNo);
		assert ReviewTokens.length == indices.length;
		this.Indices = new int[ indices.length ];
		for(int i=0; i<indices.length; i++)
			this.Indices[i] = indices[i];
	}
	
	public void indexWords(Map<String,Integer> WordsIndexer)
	{
		Indices = new int[ ReviewTokens.length ];
		for(int i=0; i<ReviewTokens.length; i++)
			if (WordsIndexer.containsKey(ReviewTokens[i]))
				Indices[i] = WordsIndexer.get(ReviewTokens[i]);
			else
				Indices[i] = WordsIndexer.get(DataSet.UNK);
	}
	
	@Override
	public Collection<Integer> getFeatures() 
	{
		List<Integer> Features = new ArrayList<Integer>( Indices.length );
	    for(int index : Indices)
	    	Features.add(index);
	    return Features;
	}

	@Override
	public Integer getLabel() 
	{
		return Label;
	}
	
	@Override
	public String toString()
	{
		String retString = Index + " // ";
		for (int i=0; i<ReviewTokens.length; i++)
			retString += ReviewTokens[i] + " ";
		return retString;
	}
	
	public int[] getIndices()
	{
		return Indices;
	}
	
	public int getDataIndex()
	{
		return Index;
	}
}
