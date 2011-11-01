package org.imgscalr.op;

import java.awt.Color;
import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.List;

import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.imgscalr.Scalr.Rotation;

/*
 * TODO: The Builder pattern dictates that the constructor accepts all required
 * params, and all the extra build methods are all optional.
 * 
 * In that case, the builder should accept:
 * 1. the image that is to be operated on
 * 2. boolean flag used to indicate if the ops should be done Sync or Async -- I
 * don't like this because it hides the fact that this builder is using a concrete
 * class from a super package.
 * 
 *  NOTE: adding a "BufferedImage build()" method that executes all the pending
 *  operations, and keeps the overloaded "apply" method out of Scalr is nice,
 *  but I don't like that this will hide the impl details of HOW it is done
 *  inside the builder.
 *  
 *  Unless that is normally OK? Need to mull longer.
 */
public class OpBuilder {
	protected List<IOp> opList;

	public OpBuilder() {
		opList = new ArrayList<IOp>(8);
	}

	@Override
	public String toString() {
		return getClass().getName() + "@" + hashCode() + " [ops={"
				+ opList.toString() + "}]";
	}

	public OpBuilder apply(BufferedImageOp... ops)
			throws IllegalArgumentException {
		opList.add(new ApplyOp(ops));
		return this;
	}

	public OpBuilder apply(List<BufferedImageOp> opList)
			throws IllegalArgumentException {
		this.opList.add(new ApplyOp(opList));
		return this;
	}

	public OpBuilder crop(int width, int height)
			throws IllegalArgumentException {
		return crop(0, 0, width, height);
	}

	public OpBuilder crop(int x, int y, int width, int height)
			throws IllegalArgumentException {
		opList.add(new CropOp(x, y, width, height));
		return this;
	}

	public OpBuilder pad(int padding) throws IllegalArgumentException {
		return pad(padding, Color.BLACK);
	}

	public OpBuilder pad(int padding, Color color)
			throws IllegalArgumentException {
		opList.add(new PadOp(padding, color));
		return this;
	}

	public OpBuilder resize(int targetSize) throws IllegalArgumentException {
		return resize(Method.AUTOMATIC, Mode.AUTOMATIC, targetSize, targetSize);
	}

	public OpBuilder resize(Method method, int targetSize)
			throws IllegalArgumentException {
		return resize(method, Mode.AUTOMATIC, targetSize, targetSize);
	}

	public OpBuilder resize(Mode mode, int targetSize)
			throws IllegalArgumentException {
		return resize(Method.AUTOMATIC, mode, targetSize, targetSize);
	}

	public OpBuilder resize(Method method, Mode mode, int targetSize)
			throws IllegalArgumentException {
		return resize(method, mode, targetSize, targetSize);
	}

	public OpBuilder resize(int targetWidth, int targetHeight)
			throws IllegalArgumentException {
		return resize(Method.AUTOMATIC, Mode.AUTOMATIC, targetWidth,
				targetHeight);
	}

	public OpBuilder resize(Method method, int targetWidth, int targetHeight)
			throws IllegalArgumentException {
		return resize(method, Mode.AUTOMATIC, targetWidth, targetHeight);
	}

	public OpBuilder resize(Mode mode, int targetWidth, int targetHeight)
			throws IllegalArgumentException {
		return resize(Method.AUTOMATIC, mode, targetWidth, targetHeight);
	}

	public OpBuilder resize(Method method, Mode mode, int targetWidth,
			int targetHeight) throws IllegalArgumentException {
		opList.add(new ResizeOp(method, mode, targetWidth, targetHeight));
		return this;
	}

	public OpBuilder rotate(Rotation rotation) throws IllegalArgumentException {
		opList.add(new RotateOp(rotation));
		return this;
	}

	public List<IOp> toList() {
		return opList;
	}
}